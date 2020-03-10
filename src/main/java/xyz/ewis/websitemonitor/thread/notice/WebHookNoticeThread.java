package xyz.ewis.websitemonitor.thread.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.dto.send.WebHookInfoDTO;

import java.util.Objects;

/**
 * WebHookNoticeThread
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@Slf4j
public class WebHookNoticeThread extends BaseNoticeThread {
    private WebsiteJobDTO websiteJobDTO;
    private WebHookInfoDTO webHookInfoDTO;
    private WebClient webClient;
    private String message;


    public WebHookNoticeThread(WebsiteJobDTO websiteJobDTO) {
        super(websiteJobDTO);
        this.websiteJobDTO = websiteJobDTO;


        NoticeMethodInfoDTO sendMethodInfo = this.websiteJobDTO.getNoticeMethod();

        if (Objects.isNull(sendMethodInfo)) {
            log.warn(" websiteJob {} can't find sendMethod ", websiteJobDTO.getName());
        }

        this.webHookInfoDTO = (WebHookInfoDTO) sendMethodInfo.getNoticeInfo();
        webClient = WebClient
                .builder()
                .baseUrl(webHookInfoDTO.getUrl()).build();
    }

    public String getMessage() {
        return recover ? websiteJobDTO.getMessage() + " [RECOVER]" : websiteJobDTO.getMessage();
    }

    @Override
    protected void send() {

        Object body = webHookInfoDTO.body(getMessage());

//        todo 容错
        webClient.post().syncBody(body).retrieve().bodyToMono(String.class).block();

    }
}
