package xyz.ewis.websitemonitor.dto.watch;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xyz.ewis.websitemonitor.enums.WatchTypeEnum;

/**
 * WatchKeyWordInfoDTO
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class WatchKeyWordMethodDTO extends BaseWatchMethodDTO {
    String keyWord;
    boolean appear;
    WatchTypeEnum watchTypeEnum = WatchTypeEnum.KEY_WORD;

    public static WatchKeyWordMethodDTO newInstant() {
        return new WatchKeyWordMethodDTO();
    }

}
