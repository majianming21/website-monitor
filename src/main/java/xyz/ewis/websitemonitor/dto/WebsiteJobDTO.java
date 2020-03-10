package xyz.ewis.websitemonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ewis.websitemonitor.dto.watch.BaseWatchMethodDTO;
import xyz.ewis.websitemonitor.enums.NoticeTypeEnum;

import java.util.Date;

/**
 * WebsiteJobDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebsiteJobDTO {
    Integer id;

    String name;

    Integer websiteId;


    String cronExpression;

    Date nextValidTime;


    Integer noticeMethodId;

    NoticeMethodInfoDTO noticeMethod;


    BaseWatchMethodDTO watchMethod;


    WebsiteDTO websiteInfo;

    Integer status;

    /**
     * 通知机制
     */
    NoticeTypeEnum noticeType;
    /**
     * 设定关闭通知的次数
     */
    Integer setRecoverCount;

    /**
     * 目前已经通知的次数
     */
    Integer leftRecoverCount;

    String message;


}
