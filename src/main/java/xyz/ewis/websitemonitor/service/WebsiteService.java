package xyz.ewis.websitemonitor.service;

import xyz.ewis.websitemonitor.dto.WebsiteDTO;

import java.util.Collection;
import java.util.List;

/**
 * WebsiteService
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
public interface WebsiteService {

    List<WebsiteDTO> queryAllEnableSite();

    List<WebsiteDTO> queryByIds(Collection<Integer> ids);
}
