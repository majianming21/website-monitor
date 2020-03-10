package xyz.ewis.websitemonitor.jobs;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import xyz.ewis.websitemonitor.WatchFeature;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.enums.NoticeTypeEnum;
import xyz.ewis.websitemonitor.service.WebsiteJobService;
import xyz.ewis.websitemonitor.thread.NoticeThreadFactory;
import xyz.ewis.websitemonitor.thread.WatchThreadFactory;
import xyz.ewis.websitemonitor.thread.notice.BaseNoticeThread;
import xyz.ewis.websitemonitor.thread.watch.BaseWatchThread;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ScheduledJob
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
@Component
@Slf4j
public class ScheduledJob {
    private final String cronExpression = "*/5 * * * * *";
    @Autowired
    @Qualifier(value = "watchJobExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private WebsiteJobService websiteJobService;
    Queue<Future<WatchFeature>> watchFutureQueue = new LinkedBlockingDeque<>();
    BlockingQueue<WebsiteJobDTO> resetCountWebsiteJobs = new LinkedBlockingDeque<>();
    LinkedBlockingDeque<WebsiteJobDTO> decreaseCountWebsiteJobs = new LinkedBlockingDeque<>();
    Queue<Future<WatchFeature>> watchStatusQueue = new LinkedBlockingDeque<>();

    @Async
    @Scheduled(cron = cronExpression)
    public void watchJob() {
        List<WebsiteJobDTO> websiteJobDTOS = websiteJobService.loadByDelayFromNow(5);

        for (WebsiteJobDTO websiteJobDTO : websiteJobDTOS) {
            BaseWatchThread baseWatchThread = WatchThreadFactory.create(websiteJobDTO);
            Future<WatchFeature> submit = taskExecutor.submit(baseWatchThread);
            watchFutureQueue.add(submit);
        }
    }

    private Future<WatchFeature> getDone(Queue<Future<WatchFeature>> watchFutureQueue) {
        while (!watchFutureQueue.isEmpty()) {
            Future<WatchFeature> peek = watchFutureQueue.peek();
            if (peek.isDone()) {
                watchFutureQueue.remove(peek);
                return peek;
            }
            if (peek.isCancelled()) {
                watchFutureQueue.remove(peek);
            }
        }
        return null;
    }

    @Async
    @Scheduled(cron = cronExpression)
    public void notice() {
        if (watchFutureQueue.isEmpty()) {
            return;
        }
        Future<WatchFeature> done;
        while (Objects.nonNull(done = getDone(watchFutureQueue))) {
            WatchFeature watchFeature = null;
            try {
                watchFeature = done.get();
            } catch (InterruptedException | ExecutionException ignored) {

            }
            assert watchFeature != null;
            if (watchFeature.isWatched()) {
                noticeWatchMessageIfNeed(watchFeature.getWebsiteJobDTO());
                preDecreaseCountIfNeed(watchFeature.getWebsiteJobDTO());
            } else {
                noticeRecoverMessageIfNeed(watchFeature.getWebsiteJobDTO());
                preResetCountIfNeed(watchFeature.getWebsiteJobDTO());
            }
        }
        updateCount();
    }

    private void updateCount() {
        List<WebsiteJobDTO> transList = Lists.newArrayListWithCapacity(resetCountWebsiteJobs.size());
        resetCountWebsiteJobs.drainTo(transList);
        websiteJobService.resetNoticeLeftCount(transList);
        transList = Lists.newArrayListWithCapacity(decreaseCountWebsiteJobs.size());
        decreaseCountWebsiteJobs.drainTo(transList);
        websiteJobService.decreaseNoticeLeftCount(transList);
    }

    private void preResetCountIfNeed(WebsiteJobDTO websiteJobDTO) {
        if (websiteJobDTO.getLeftRecoverCount().equals(websiteJobDTO.getSetRecoverCount())) {
            return;
        }
        NoticeTypeEnum noticeType = websiteJobDTO.getNoticeType();
        if (noticeType == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_CLOSE) {
            return;
        }
        resetCountWebsiteJobs.add(websiteJobDTO);
    }

    private void noticeRecoverMessageIfNeed(WebsiteJobDTO websiteJobDTO) {
        if (websiteJobDTO.getNoticeType() != NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER_AND_NOTICE) {
            return;
        }
        if (websiteJobDTO.getLeftRecoverCount().equals(websiteJobDTO.getSetRecoverCount())) {
            return;
        }
        BaseNoticeThread baseSenderThread = NoticeThreadFactory.createRecover(websiteJobDTO);
        taskExecutor.submit(baseSenderThread);
    }

    private void preDecreaseCountIfNeed(WebsiteJobDTO websiteJobDTO) {
        if (websiteJobDTO.getLeftRecoverCount() == 0) {
            return;
        }
        NoticeTypeEnum noticeType = websiteJobDTO.getNoticeType();
        if (noticeType == NoticeTypeEnum.ALWAYS) {
            return;
        }
        decreaseCountWebsiteJobs.add(websiteJobDTO);
    }

    private void noticeWatchMessageIfNeed(WebsiteJobDTO websiteJobDTO) {
        NoticeTypeEnum noticeType = websiteJobDTO.getNoticeType();
        if (Objects.isNull(noticeType)) {
            noticeType = NoticeTypeEnum.defaultType();
        }
        List<NoticeTypeEnum> noticeTypeEnumLists = Lists.newArrayList(NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_CLOSE, NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER_AND_NOTICE, NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER);
        if (noticeTypeEnumLists.contains(noticeType) && websiteJobDTO.getLeftRecoverCount() == 0) {
            return;
        }
        BaseNoticeThread baseSenderThread = NoticeThreadFactory.create(websiteJobDTO);
        taskExecutor.submit(baseSenderThread);
    }


//
//    private void updateJobIfCloseNotice() {
//        NoticeTypeEnum noticeType = websiteJobDTO.getNoticeType();
//        if (Objects.isNull(noticeType)) {
//            noticeType = NoticeTypeEnum.defaultType();
//            websiteJobDTO.setNoticeType(noticeType);
//        }
//        if (noticeType == NoticeTypeEnum.ALWAYS) {
//            return;
//        }
//        if (noticeType == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_CLOSE) {
//            return;
//        }
//        if (noticeType == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER || noticeType == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER_AND_NOTICE) {
//            websiteJobDTO.setLeftRecoverCount(websiteJobDTO.getSetRecoverCount());
//        }
//    }
}
