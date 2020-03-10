package xyz.ewis.websitemonitor.utils;

import org.quartz.CronExpression;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * CronExpressionSetUtil
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public class CronExpressionSetUtil {
    private CronExpressionSetUtil() {

    }

    public static WebsiteJobDTO setNextValidTime(WebsiteJobDTO websiteJobDTO, Date fromTime) {
        if (Objects.isNull(websiteJobDTO) || Objects.isNull(fromTime)) {
            return websiteJobDTO;
        }
        String cronExpression = websiteJobDTO.getCronExpression();
        Date nextValidTimeAfter = null;
        try {
            nextValidTimeAfter = new CronExpression(cronExpression).getNextValidTimeAfter(fromTime);
        } catch (ParseException ignored) {

        }
        websiteJobDTO.setNextValidTime(nextValidTimeAfter);
        return websiteJobDTO;
    }
}
