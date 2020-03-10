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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ExcutorJob
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
@Component
@Slf4j
public class ExcutorJob {
    private final String cronEx = "*/5 * * * * *";
    @Autowired
    @Qualifier(value = "watchJobExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private WebsiteJobService websiteJobService;
    Queue<Future<WatchFeature>> watchFutureQueue = new LinkedBlockingDeque<>();
    Queue<Future<WatchFeature>> watchStatusQueue = new LinkedBlockingDeque<>();

    @Async
    @Scheduled(cron = cronEx)
    public void check() {
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
                return peek;
            }
            if (peek.isCancelled()) {
                watchFutureQueue.remove(peek);
            }
        }
        return null;
    }

    @Async
    @Scheduled(cron = cronEx)
    public void notice() {
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
                decreaseCountIfNeed(watchFeature.getWebsiteJobDTO());
            } else {
                noticeRecoverMessageIfNeed(watchFeature.getWebsiteJobDTO());
                resetCountIfNeed(watchFeature.getWebsiteJobDTO());
            }

        }
    }

    private void resetCountIfNeed(WebsiteJobDTO websiteJobDTO) {

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

    private void decreaseCountIfNeed(WebsiteJobDTO websiteJobDTO) {

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

    //    @Async
//    @Scheduled(cron = cronEx)
    public void update() {
        List<WebsiteJobDTO> websiteJobDTOS = new ArrayList<>();
        List<WebsiteJobDTO> resetRecoverCountJob = new ArrayList<>();
        while (true) {
            Future<WatchFeature> future = watchFutureQueue.peek();
            if (Objects.isNull(future)) {
                break;
            }
            if (future.isCancelled()) {
                watchFutureQueue.remove(future);
                continue;
            }
            if (!future.isDone()) {
                continue;
            }
            watchFutureQueue.remove(future);

            if (future.isDone()) {
                WatchFeature watchFeature = null;
                try {
                    watchFeature = future.get();
                } catch (InterruptedException | ExecutionException e) {

                }
//                if (watchFeature.isNotWatch()) {
////                    websiteJobDTOS.add(feature.getWebsiteJobDTO());
//                    resetRecoverCountJob.add(watchFeature.getWebsiteJobDTO());
//                }
            }
        }
        websiteJobService.resetNoticeLeftCount(resetRecoverCountJob);
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
