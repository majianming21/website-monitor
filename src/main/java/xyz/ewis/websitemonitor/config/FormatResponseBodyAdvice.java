package xyz.ewis.websitemonitor.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * Advice
 *
 * @author majianming
 * @date 2020-02-17 20:55
 */
@ControllerAdvice
@Slf4j
public class FormatResponseBodyAdvice /*implements ResponseBodyAdvice*/ {
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class converterType) {
//        RawResponse annotation1 = methodParameter.getMethod().getAnnotation(RawResponse.class);
//        if (Objects.nonNull(annotation1)){
//            return false;
//        }
//        RawResponse annotation = methodParameter.getMethod().getDeclaringClass().getAnnotation(RawResponse.class);
//        return !Objects.nonNull(annotation);
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        if (Objects.nonNull(body) && body instanceof ResultDTO) {
//            return body;
//        }
//        return ResultDTO.successfy(body);
//
//    }
//    @ExceptionHandler(value = {Exception.class})
//    @ResponseBody
//    public ResultDTO handleExceptions(final Exception ex) {
//        if (ex instanceof BtrDataInvalidException) {
//            return ResultDTO.fail(StateCode.SYSTEM_ERROR,ex.getMessage());
//        }
//        if (ex instanceof BtrArgsInvalidException) {
//            return ResultDTO.fail(StateCode.ILLEGAL_ARGS,ex.getMessage());
//        }
//        log.error("未知异常 ",ex);
//        return ResultDTO.fail(StateCode.SYSTEM_ERROR,ex.getMessage());
//    }
}