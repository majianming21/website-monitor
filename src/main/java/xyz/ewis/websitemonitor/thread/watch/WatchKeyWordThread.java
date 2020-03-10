package xyz.ewis.websitemonitor.thread.watch;

import org.springframework.web.reactive.function.client.WebClient;
import xyz.ewis.websitemonitor.dto.WebsiteDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.dto.watch.WatchKeyWordMethodDTO;

import java.util.Objects;

/**
 * WatchKeyWordThread
 *
 * @author MAJANNING
 * @date 2020/3/6
 */
public class WatchKeyWordThread extends BaseWatchThread {
    private WebClient webClient;
    private WebsiteJobDTO websiteJobDTO;

    public WatchKeyWordThread(WebsiteJobDTO websiteJobDTO) {
        super(websiteJobDTO);
        this.websiteJobDTO = websiteJobDTO;
        WebsiteDTO websiteInfo = websiteJobDTO.getWebsiteInfo();

        webClient = WebClient
                .builder()
                .baseUrl(websiteInfo.getUrl()).build();
    }

    @Override
    protected boolean watch() {
        WatchKeyWordMethodDTO watchInfo = (WatchKeyWordMethodDTO) websiteJobDTO.getWatchMethod();
        if (Objects.isNull(watchInfo)) {
            return false;
        }
        String content = webClient.get().retrieve().bodyToMono(String.class).block();
        String keyWord = watchInfo.getKeyWord();
        return content.contains(keyWord) == watchInfo.isAppear();
    }
}
