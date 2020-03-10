package xyz.ewis.websitemonitor.dto.watch;

import xyz.ewis.websitemonitor.enums.WatchTypeEnum;

/**
 * BaseWatchInfoDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public abstract class BaseWatchMethodDTO {
    public abstract WatchTypeEnum getWatchTypeEnum();
}
