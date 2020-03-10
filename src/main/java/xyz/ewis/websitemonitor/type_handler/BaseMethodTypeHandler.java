package xyz.ewis.websitemonitor.type_handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;


/**
 * BaseMethodTypeHandler
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
public abstract class BaseMethodTypeHandler<T> implements TypeHandler<T> {


    static Reflections reflections = new Reflections("xyz.ewis.websitemonitor.dto");

    @SuppressWarnings("unchecked")
    private Set<Class<? extends T>> getSubClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return reflections.getSubTypesOf(tClass);
    }


    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
        String value = JSONObject.toJSONString(t);
        preparedStatement.setString(i, value);
    }

    @Override
    public T getResult(ResultSet resultSet, String name) throws SQLException {
        String value = resultSet.getString(name);
        return getResult(value);
    }

    @Override
    public T getResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return getResult(value);
    }

    @Override
    public T getResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return getResult(value);
    }

    private T getResult(String value) {
        Set<Class<? extends T>> subClass = getSubClass();
        for (Class<? extends T> aClass : subClass) {
            T t = JSONObject.parseObject(value, aClass);
            if (Objects.isNull(t)) {
                continue;
            }
            if (isInstance(t, aClass)) {
                return t;
            }
        }
        return null;
    }


    public abstract boolean isInstance(T value, Class<? extends T> aClass);
}
