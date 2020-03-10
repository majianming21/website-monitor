package xyz.ewis.websitemonitor.thread.watch;

import lombok.extern.slf4j.Slf4j;
import xyz.ewis.websitemonitor.WatchFeature;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.util.concurrent.Callable;

/**
 * BaseWatchThread
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@Slf4j
public abstract class BaseWatchThread implements Callable<WatchFeature> {
    protected WebsiteJobDTO websiteJobDTO;

    public BaseWatchThread(WebsiteJobDTO websiteJobDTO) {
        this.websiteJobDTO = websiteJobDTO;
    }

    @Override
    public WatchFeature call() {
        boolean watch = watch();
        if (log.isDebugEnabled()) {
            log.debug(" 任务{} 状态？{}", websiteJobDTO.getName(), watch);
        }
        return WatchFeature.getInstance(websiteJobDTO).setWatched(watch);
    }


    protected abstract boolean watch();
}
