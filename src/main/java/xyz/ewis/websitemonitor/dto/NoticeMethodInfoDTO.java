package xyz.ewis.websitemonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.ewis.websitemonitor.dto.send.BaseNoticeInfoDTO;

/**
 * NoticeMethodInfoDTO
 *
 * @author MAJANNING
 * @date 2020/3/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeMethodInfoDTO {
    Integer id;
    String name;
    BaseNoticeInfoDTO noticeInfo;

    Integer status;
}
