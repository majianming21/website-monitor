package xyz.ewis.websitemonitor.dao;

import org.apache.ibatis.annotations.Select;
import xyz.ewis.websitemonitor.dto.NoticeMethodInfoDTO;

import java.util.Collection;
import java.util.List;

/**
 * NoticeMethodDAO
 * fixme sql错误
 *
 * @author MAJANNING
 * @date 2020/3/9
 */
public interface NoticeMethodDAO {


    @Select("select *  from notice_methods")
    List<NoticeMethodInfoDTO> queryAll();

    @Select("select *  from notice_methods where status=1")
    List<NoticeMethodInfoDTO> queryByIds(Collection<Integer> ids);
}
