package xyz.ewis.websitemonitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ewis.websitemonitor.dao.NoticeMethodDAO;
import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;
import xyz.ewis.websitemonitor.service.NoticeMethodService;
import xyz.ewis.websitemonitor.utils.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * NoticeMethodServiceImpl
 *
 * @author MAJANNING
 * @date 2020/3/9
 */
@Service
public class NoticeMethodServiceImpl implements NoticeMethodService {
    @Autowired
    private NoticeMethodDAO noticeMethodDAO;


    @Override
    public List<NoticeMethodInfoDTO> queryByIds(Collection<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return noticeMethodDAO.queryByIds(ids);
    }

    @Override
    public List<NoticeMethodInfoDTO> queryByIdsAndEnable(Collection<Integer> ids) {
        return queryByIds(ids).stream().filter(sendMethodInfoDTO -> sendMethodInfoDTO.getStatus() == 1).collect(Collectors.toList());
    }
}
