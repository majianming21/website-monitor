package xyz.ewis.websitemonitor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NoticeTypeEnum
 *
 * @author MAJANNING
 * @date 2020/3/8
 */
@AllArgsConstructor
@Getter
public enum NoticeTypeEnum {
    /**
     * 总是通知
     */
    ALWAYS(1, "总是通知"),
    /**
     * 通知前N次后关闭任务检查
     */
    HAS_PRE_N_NOTICES_WITH_CLOSE(2, ""),
    /**
     * 通知前N次并在状态变更时恢复检查
     */
    HAS_PRE_N_NOTICES_WITH_RECOVER(3, ""),
    /**
     * 通知前N次并在状态变更时恢复检查 同时通知恢复
     */
    HAS_PRE_N_NOTICES_WITH_RECOVER_AND_NOTICE(4, "");

    int index;
    String name;

    public static NoticeTypeEnum valueOf(int index){
        for (NoticeTypeEnum noticeTypeEnum:values()){
            if (index==noticeTypeEnum.index){
                return noticeTypeEnum;
            }
        }
        return NoticeTypeEnum.defaultType();
    }
    public static NoticeTypeEnum defaultType(){
        return NoticeTypeEnum.ALWAYS;
    }

}
