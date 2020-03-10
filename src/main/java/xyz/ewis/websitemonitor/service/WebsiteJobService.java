package xyz.ewis.websitemonitor.service;

import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.util.Collection;
import java.util.List;

/**
 * WebsiteJobService
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public interface WebsiteJobService {

    List<WebsiteJobDTO> queryByWebsiteIds(Collection<Integer> websiteIds);

    List<WebsiteJobDTO> loadByDelayFromNow(long delaySecond);

    void save(Collection<WebsiteJobDTO> websiteJobDTOS);

    int resetNoticeLeftCount(Collection<WebsiteJobDTO> websiteJobDTOS );

}
