package xyz.ewis.websitemonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.service.WebsiteJobService;
import xyz.ewis.websitemonitor.utils.Lists;

import java.util.List;

/**
 * WebsiteController
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@RestController
@RequestMapping("/websites")
public class WebsiteController {
    @Autowired
    private WebsiteJobService websiteJobService;

    @GetMapping("/{websiteId:\\d+}/jobs")
    public List<WebsiteJobDTO> queryByWebsiteId(@PathVariable Integer websiteId) {
        return websiteJobService.queryByWebsiteIds(Lists.newArrayList(websiteId));
    }
}
