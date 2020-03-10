package xyz.ewis.websitemonitor.interceptor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;
import xyz.ewis.websitemonitor.dto.WebsiteDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;
import xyz.ewis.websitemonitor.service.NoticeMethodService;
import xyz.ewis.websitemonitor.service.WebsiteService;
import xyz.ewis.websitemonitor.utils.BinaryOperatorUtil;
import xyz.ewis.websitemonitor.utils.CollectionUtils;
import xyz.ewis.websitemonitor.utils.CronExpressionSetUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * WebsiteJobDTOInterceptor
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
@Aspect
@Component
public class WebsiteJobDTOInterceptor {
    @Autowired
    private NoticeMethodService noticeMethodService;
    @Autowired
    private WebsiteService websiteService;

    @AfterReturning(returning = "websiteJobDTOS", pointcut = "execution(java.util.List<xyz.ewis.websitemonitor.dto.WebsiteJobDTO> xyz.ewis.websitemonitor.dao.WebsiteJobDAO.*(..))")
    public Object setWebsiteInfo(List<WebsiteJobDTO> websiteJobDTOS) {
        if (CollectionUtils.isEmpty(websiteJobDTOS)) {
            return websiteJobDTOS;
        }
        for (WebsiteJobDTO websiteJobDTO : websiteJobDTOS) {
            setWebsiteInfo(websiteJobDTO);
        }
        return setNoticeInfo(websiteJobDTOS);
    }

    private List<WebsiteJobDTO> setNoticeInfo(List<WebsiteJobDTO> websiteJobDTOS) {
        Set<Integer> noticeMethodIds= websiteJobDTOS.stream().map(WebsiteJobDTO::getNoticeMethodId).collect(Collectors.toSet());
        List<NoticeMethodInfoDTO> noticeMethodInfoDTOS = noticeMethodService.queryByIds(noticeMethodIds);

        //set
        Map<Integer, NoticeMethodInfoDTO> noticeMethodInfoDTOMap = noticeMethodInfoDTOS.stream().collect(Collectors.toMap(NoticeMethodInfoDTO::getId, Function.identity(), BinaryOperatorUtil.first()));

        websiteJobDTOS.forEach(websiteJobDTO -> {
            Integer sendMethodId = websiteJobDTO.getNoticeMethodId();
            NoticeMethodInfoDTO noticeMethodInfoDTO = noticeMethodInfoDTOMap.get(sendMethodId);
            websiteJobDTO.setNoticeMethod(noticeMethodInfoDTO);
        });



        List<WebsiteDTO> websiteDTOS = websiteService.queryByIds(websiteJobDTOS.stream().map(WebsiteJobDTO::getWebsiteId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(websiteDTOS)) {
            return Collections.emptyList();
        }

        Map<Integer, WebsiteDTO> websiteMaps = websiteDTOS.stream().collect(Collectors.toMap(WebsiteDTO::getId, Function.identity(), BinaryOperatorUtil.first()));

        if (CollectionUtils.isEmpty(websiteJobDTOS)) {
            return Collections.emptyList();
        }
        websiteJobDTOS.forEach(websiteJobDTO -> {
            Integer websiteId = websiteJobDTO.getWebsiteId();
            WebsiteDTO websiteDTO = websiteMaps.get(websiteId);
            if (Objects.isNull(websiteDTO)) {
                throw new IllegalStateException("website id not match website");
            }
            websiteJobDTO.setWebsiteInfo(websiteDTO);
        });
        return websiteJobDTOS;




    }


    public Object setWebsiteInfo(WebsiteJobDTO websiteJobDTO) {
        Date date = new Date();
        CronExpressionSetUtil.setNextValidTime(websiteJobDTO, date);
        return websiteJobDTO;
    }
}
