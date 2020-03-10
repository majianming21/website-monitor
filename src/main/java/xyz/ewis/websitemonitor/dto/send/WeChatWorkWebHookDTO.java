package xyz.ewis.websitemonitor.dto.send;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import xyz.ewis.websitemonitor.enums.SendTypeEnum;

/**
 * WeChatWorkWebHookDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WeChatWorkWebHookDTO extends WebHookInfoDTO {

    public static WeChatWorkWebHookDTO newInstant(){
        return new WeChatWorkWebHookDTO();
    }

    String key;
    @Getter
    SendTypeEnum sendTypeEnum = SendTypeEnum.WE_CHAT_WORK;


    @Override
    public String getUrl() {
        return "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
    }

    @Override
    public Object body(String message) {
        JSONObject requestBody = new JSONObject();
        JSONObject text = new JSONObject();
        text.put("content", message);
        requestBody.put("msgtype", "text");
        requestBody.put("text", text);
        return requestBody;
    }

}
