package xyz.ewis.websitemonitor.service;

import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;

import java.util.Collection;
import java.util.List;

/**
 * NoticeMethodService
 * 通知消息方式Service
 *
 * @author MAJANNING
 * @date 2020/3/9
 */
public interface NoticeMethodService {
    List<NoticeMethodInfoDTO> queryByIds(Collection<Integer> ids);

    /**
     * 查询消息通知方式 并且是启用状态的
     *
     * @param ids
     * @return
     */
    List<NoticeMethodInfoDTO> queryByIdsAndEnable(Collection<Integer> ids);
}
