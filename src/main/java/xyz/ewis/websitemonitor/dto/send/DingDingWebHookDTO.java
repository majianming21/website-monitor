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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DingDingWebHookDTO extends WebHookInfoDTO {

    String key;
    SendTypeEnum sendTypeEnum = SendTypeEnum.DING_DING;


    @Override
    public String getUrl() {
        return "https://dingding.weixin.qq.com/cgi-bin/webhook/send?key=" + key;
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
