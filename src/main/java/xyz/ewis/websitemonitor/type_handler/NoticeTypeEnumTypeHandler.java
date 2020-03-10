package xyz.ewis.websitemonitor.type_handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import xyz.ewis.websitemonitor.enums.NoticeTypeEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * NoticeTypeEnumTypeHandler
 *
 * @author MAJANNING
 * @date 2020/3/8
 */
@MappedTypes(value = {NoticeTypeEnum.class})
public class NoticeTypeEnumTypeHandler extends BaseTypeHandler<NoticeTypeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, NoticeTypeEnum noticeTypeEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, noticeTypeEnum.getIndex());
    }

    @Override
    public NoticeTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int index = resultSet.getInt(s);
        return NoticeTypeEnum.valueOf(index);
    }

    @Override
    public NoticeTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int index = resultSet.getInt(i);
        return NoticeTypeEnum.valueOf(index);
    }

    @Override
    public NoticeTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int index = callableStatement.getInt(i);
        return NoticeTypeEnum.valueOf(index);
    }
}
