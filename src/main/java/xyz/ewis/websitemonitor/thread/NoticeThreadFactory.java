package xyz.ewis.websitemonitor.thread;

import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.dto.send.BaseNoticeInfoDTO;
import xyz.ewis.websitemonitor.dto.send.WebHookInfoDTO;
import xyz.ewis.websitemonitor.thread.notice.BaseNoticeThread;
import xyz.ewis.websitemonitor.thread.notice.WebHookNoticeThread;

import java.util.Objects;

/**
 * WatchThreadFactory
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
public class NoticeThreadFactory {
    public static BaseNoticeThread createRecover(WebsiteJobDTO websiteJobDTO) {
        BaseNoticeThread baseNoticeThread = create(websiteJobDTO);
        baseNoticeThread.setRecover(true);
        return baseNoticeThread;
    }

    public static BaseNoticeThread create(WebsiteJobDTO websiteJobDTO) {
        NoticeMethodInfoDTO sendMethodInfo = websiteJobDTO.getNoticeMethod();
        if (Objects.isNull(sendMethodInfo)) {

            throw new IllegalStateException("not notice type ");
        }
        BaseNoticeInfoDTO sendMethod = sendMethodInfo.getNoticeInfo();
        if (Objects.isNull(sendMethod)) {
            throw new IllegalStateException("not notice type ");
        }
        if (sendMethod instanceof WebHookInfoDTO) {
            return new WebHookNoticeThread(websiteJobDTO);
        }
        throw new IllegalStateException("not notice type ");

    }
}
