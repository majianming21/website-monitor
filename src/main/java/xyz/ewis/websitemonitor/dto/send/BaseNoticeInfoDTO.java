package xyz.ewis.websitemonitor.dto.send;

import xyz.ewis.websitemonitor.enums.SendTypeEnum;

/**
 * BaseNoticeInfoDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public abstract class BaseNoticeInfoDTO {

    public abstract SendTypeEnum getSendTypeEnum();
}
