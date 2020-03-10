package xyz.ewis.websitemonitor.thread;

import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.dto.watch.BaseWatchMethodDTO;
import xyz.ewis.websitemonitor.dto.watch.WatchKeyWordMethodDTO;
import xyz.ewis.websitemonitor.thread.watch.BaseWatchThread;
import xyz.ewis.websitemonitor.thread.watch.WatchKeyWordThread;

/**
 * WatchThreadFactory
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
public class WatchThreadFactory {

    public static BaseWatchThread create(WebsiteJobDTO websiteJobDTO) {
        BaseWatchMethodDTO watchInfo = websiteJobDTO.getWatchMethod();
        if (watchInfo instanceof WatchKeyWordMethodDTO) {
            return new WatchKeyWordThread(websiteJobDTO);
        }
        throw new IllegalStateException("not watch thread type ");
    }
}
