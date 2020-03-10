package xyz.ewis.websitemonitor.type_handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.MappedTypes;
import xyz.ewis.websitemonitor.dto.send.BaseNoticeInfoDTO;

import java.lang.reflect.InvocationTargetException;

/**
 * SendMethodTypeHandler
 *
 * @author MAJANNING
 * @date 2020/3/7
 */
@Slf4j
@MappedTypes(value = {BaseNoticeInfoDTO.class})
public class SendMethodTypeHandler extends BaseMethodTypeHandler<BaseNoticeInfoDTO> {

    @Override
    public boolean isInstance(BaseNoticeInfoDTO value, Class<? extends BaseNoticeInfoDTO> aClass) {
        BaseNoticeInfoDTO newInstance;
        try {
            newInstance = aClass.getConstructor().newInstance();
        } catch (InstantiationException ignore) {
//                    can not be instant ,is abstract class
            return false;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return false;
        }
        return newInstance.getSendTypeEnum() == value.getSendTypeEnum();
    }
}
