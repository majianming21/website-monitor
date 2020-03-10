package xyz.ewis.websitemonitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ewis.websitemonitor.dao.WebsiteDAO;
import xyz.ewis.websitemonitor.dto.WebsiteDTO;
import xyz.ewis.websitemonitor.service.WebsiteService;

import java.util.Collection;
import java.util.List;

/**
 * WebsiteServiceImpl
 *
 * @author MAJANNING
 * @date 2020/3/8
 */
@Service
public class WebsiteServiceImpl implements WebsiteService {
    @Autowired
    private WebsiteDAO websiteDAO;

    @Override
    public List<WebsiteDTO> queryAllEnableSite() {
        return websiteDAO.queryAllEnableSite();
    }

    @Override
    public List<WebsiteDTO> queryByIds(Collection<Integer> ids) {
        return queryAllEnableSite();
    }
}
