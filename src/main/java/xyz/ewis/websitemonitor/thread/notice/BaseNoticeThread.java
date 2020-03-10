package xyz.ewis.websitemonitor.thread.notice;

import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.util.concurrent.Callable;

/**
 * BaseSenderThread
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public abstract class BaseNoticeThread implements Callable<WebsiteJobDTO> {
    private WebsiteJobDTO websiteJobDTO;
    protected boolean recover;

    public BaseNoticeThread(WebsiteJobDTO websiteJobDTO) {
        this.websiteJobDTO = websiteJobDTO;
    }

    public void setRecover(boolean recover) {
        this.recover = recover;
    }

    @Override
    public WebsiteJobDTO call() {
        send();
        return websiteJobDTO;
    }

    protected abstract void send();

}
