package xyz.ewis.websitemonitor.dto.send;

/**
 * WebHookInfoDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public abstract class WebHookInfoDTO extends BaseNoticeInfoDTO {

    public abstract String getUrl();

    public abstract Object body(String message);
}
