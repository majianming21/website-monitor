package xyz.ewis.websitemonitor.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.ewis.websitemonitor.dto.WebsiteDTO;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.util.Collection;
import java.util.List;

/**
 * WebsiteJobDAO
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public interface WebsiteDAO {
    @Select({
            "<script>",
            "select * from websites where website_id in ",
            "<foreach item='websiteId' collection='websiteIds' separator=',' open=' (' close=')'> ",
            "#{websiteId}",
            "</foreach>",
            "</script>",
    })
    List<WebsiteDTO> queryByWebsiteIds(@Param("websiteIds") Collection<Integer> websiteIds);

    @Select({
            "select * from websites where status=1 ",
    })
    List<WebsiteDTO> queryAllEnableSite();


//    @Insert({
//            "<script>",
//            "INSERT INTO `website_jobs` (`name`, `website_id`, `cron_expression`, `watch_method`, `send_method`, `status`) VALUES ",
//            "<foreach item='websiteJob' collection='websiteJobs' separator=','> ",
//            "(#{websiteJob.name}, #{websiteJob.websiteId}, #{websiteJob.cronExpression}, #{websiteJob.watchMethod}, #{websiteJob.sendMethod}, #{websiteJob.status})",
//            "</foreach>",
//            "</script>",
//    })
//    void save(@Param("websiteJobs") Collection<WebsiteJobDTO> websiteJobDTOS);
}
