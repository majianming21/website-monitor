package xyz.ewis.websitemonitor.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

import java.util.Collection;
import java.util.List;

/**
 * WebsiteJobDAO
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public interface WebsiteJobDAO {
    @Select({
            "<script>",
            "select * from website_jobs where website_id in ",
            "<foreach item='websiteId' collection='websiteIds' separator=',' open=' (' close=')'> ",
            "#{websiteId}",
            "</foreach>",
            "</script>",
    })
    List<WebsiteJobDTO> queryByWebsiteIds(@Param("websiteIds") Collection<Integer> websiteIds);

    @Select({
            "<script>",
            "select * from website_jobs where website_id in ",
            "<foreach item='websiteId' collection='websiteIds' separator=',' open=' (' close=')'> ",
            "#{websiteId}",
            "</foreach>",
            "</script>",
    })
    List<WebsiteJobDTO> queryAllEnableJobs(@Param("websiteIds") Collection<Integer> websiteIds);


    @Insert({
            "<script>",
            "INSERT INTO `website_jobs` (`name`, `website_id`, `cron_expression`, `watch_method`, `send_method`, `status`) VALUES ",
            "<foreach item='websiteJob' collection='websiteJobs' separator=','> ",
            "(#{websiteJob.name}, #{websiteJob.websiteId}, #{websiteJob.cronExpression}, #{websiteJob.watchMethod}, #{websiteJob.sendMethod}, #{websiteJob.status})",
            "</foreach>",
            "</script>",
    })
    void save(@Param("websiteJobs") Collection<WebsiteJobDTO> websiteJobDTOS);

    @Insert({
            "<script>",
            "update `website_jobs` set left_recover_count=set_recover_count where id in ",
            "<foreach item='websiteJobId' collection='websiteJobIds' separator=',' open=' (' close=')'> ",
            "#{websiteJobId}",
            "</foreach>",
            "</script>",
    })
    int resetNoticeLeftCount(@Param("websiteJobIds") Collection<Integer> websiteJobIds);

    @Insert({
            "<script>",
            "update `website_jobs` set left_recover_count=left_recover_count-1 where left_recover_count>0 and id in ",
            "<foreach item='websiteJobId' collection='websiteJobIds' separator=',' open=' (' close=')'> ",
            "#{websiteJobId}",
            "</foreach>",
            "</script>",
    })
    int decreaseNoticeLeftCount(@Param("websiteJobIds") Collection<Integer> websiteJobIds);
}
