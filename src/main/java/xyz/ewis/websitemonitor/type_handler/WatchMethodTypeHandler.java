package xyz.ewis.websitemonitor.type_handler;

import org.apache.ibatis.type.MappedTypes;
import xyz.ewis.websitemonitor.dto.watch.BaseWatchMethodDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * WatchMethodTypeHandler
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
@MappedTypes(value = {BaseWatchMethodDTO.class})
public class WatchMethodTypeHandler extends BaseMethodTypeHandler<BaseWatchMethodDTO> {


    @Override
    public boolean isInstance(BaseWatchMethodDTO value, Class<? extends BaseWatchMethodDTO> aClass) {
        BaseWatchMethodDTO newInstance;
        try {
            newInstance = aClass.getConstructor().newInstance();
        } catch (InstantiationException ignore) {
//                    can not be instant ,is abstract class
            return false;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return false;
        }
        return newInstance.getWatchTypeEnum() == value.getWatchTypeEnum();
    }
}
