package xyz.ewis.websitemonitor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.ewis.websitemonitor.dao.WebsiteJobDAO;
import xyz.ewis.websitemonitor.dto.WebsiteDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.enums.NoticeTypeEnum;
import xyz.ewis.websitemonitor.service.WebsiteJobService;
import xyz.ewis.websitemonitor.service.WebsiteService;
import xyz.ewis.websitemonitor.utils.CollectionUtils;
import xyz.ewis.websitemonitor.utils.CronExpressionSetUtil;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WebsiteJobServiceImpl
 *
 * @author MAJANNING
 * @date 2020/3/5
 */
@Service
@Slf4j
public class WebsiteJobServiceImpl implements WebsiteJobService/*, InitializingBean */ {
    @Autowired
    private WebsiteJobDAO websiteJobDAO;
    @Autowired
    private WebsiteService websiteService;


    @Override
    public List<WebsiteJobDTO> queryByWebsiteIds(Collection<Integer> websiteIds) {
        if (CollectionUtils.isEmpty(websiteIds)) {
            return Collections.emptyList();
        }
        return websiteJobDAO.queryByWebsiteIds(websiteIds);
    }

    private List<WebsiteJobDTO> queryAllEnableJobs() {
        List<WebsiteDTO> websiteDTOS = websiteService.queryAllEnableSite();
        Set<Integer> websiteIds = websiteDTOS.stream().map(WebsiteDTO::getId).collect(Collectors.toSet());
        return websiteJobDAO.queryAllEnableJobs(websiteIds);

    }


    @Override
    public List<WebsiteJobDTO> loadByDelayFromNow(long delaySecond) {
        Instant instant = Instant.now().plusSeconds(delaySecond);
//        return websiteJobDAO.queryAllEnableJobs()
        return queryAllEnableJobs().stream().peek(s -> CronExpressionSetUtil.setNextValidTime(s, new Date()))
                .filter(websiteJobDTO -> {
                    Instant jobNextValidTimeInstant = websiteJobDTO.getNextValidTime().toInstant();
                    if (log.isDebugEnabled()) {

                        log.debug("现在时间{},任务id:{} 名称:{} 下次执行时间{} 距离下次执行时间还有 {} 秒", jobNextValidTimeInstant, websiteJobDTO.getId(), websiteJobDTO.getName(), instant, jobNextValidTimeInstant.getEpochSecond() - instant.getEpochSecond());
                    }
                    return jobNextValidTimeInstant.compareTo(instant) <= 0;
                }).collect(Collectors.toList());
    }

    @Override
    public void save(Collection<WebsiteJobDTO> websiteJobDTOS) {
        if (CollectionUtils.isEmpty(websiteJobDTOS)) {
            return;
        }
        websiteJobDAO.save(websiteJobDTOS);
    }

    @Override
    public int resetNoticeLeftCount(Collection<WebsiteJobDTO> websiteJob) {
        if (CollectionUtils.isEmpty(websiteJob)) {
            return 0;
        }
        List<Integer> websiteJobIds = websiteJob.stream().filter(l -> l.getNoticeType() == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER || l.getNoticeType() == NoticeTypeEnum.HAS_PRE_N_NOTICES_WITH_RECOVER_AND_NOTICE).map(WebsiteJobDTO::getId).collect(Collectors.toList());
        return websiteJobDAO.resetNoticeLeftCount(websiteJobIds);
    }


//    private final static List<WebsiteJobDTO> websiteJobs = new ArrayList<>();
//
//    static {
//
//        WeChatWorkWebHookDTO sendMethod = WeChatWorkWebHookDTO.newInstant();
//        sendMethod.setContent("口罩可以选广州了 快点到 https://new-mask-html.dslbuy.com/newmain.html 选吧");
//        sendMethod.setKey("6ce60972-3dd1-4305-9f6d-1393d514cba4");
//        WatchKeyWordMethodDTO keyWordMethodDTO = WatchKeyWordMethodDTO.newInstant();
//        keyWordMethodDTO.setAppear(true);
//        keyWordMethodDTO.setKeyWord("广州");
//
//        WebsiteDTO build = WebsiteDTO.builder().url("https://new-mask-html.dslbuy.com/newmain.html").build();
//        WebsiteJobDTO 大森林 = WebsiteJobDTO.builder().cronExpression("0 0 */2 * * ?").websiteId(1)
//                .name("大森林")
//                .status(1)
//                .websiteInfo(build)
//                .sendMethod(sendMethod).watchMethod(keyWordMethodDTO)
//                .build();
//        websiteJobs.add(大森林);
//    }
//
//    static {
//
//        WeChatWorkWebHookDTO sendMethod = WeChatWorkWebHookDTO.newInstant();
//        sendMethod.setContent("v2 有java 快点到 https://www.v2ex.com");
//        sendMethod.setKey("6ce60972-3dd1-4305-9f6d-1393d514cba4");
//        WatchKeyWordMethodDTO keyWordMethodDTO = WatchKeyWordMethodDTO.newInstant();
//        keyWordMethodDTO.setAppear(true);
//        keyWordMethodDTO.setKeyWord("java");
//
//        WebsiteDTO build = WebsiteDTO.builder().url("https://www.v2ex.com").build();
//        WebsiteJobDTO v2 = WebsiteJobDTO.builder().cronExpression("0 * * * * ?").websiteId(1)
//                .name("v2")
//                .status(1)
//                .websiteInfo(build)
//                .sendMethod(sendMethod).watchMethod(keyWordMethodDTO)
//                .build();
//        websiteJobs.add(v2);
//    }

}
