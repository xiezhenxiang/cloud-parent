package indi.shine.web.init;

import indi.shine.common.bean.response.ReturnT;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;

/**
 * 接口统一返回格式
 * @author xiezhenxiang 2021/9/14
 */
@RestControllerAdvice
public class BizResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }
    private final String[] excludePaths = {"/swagger-resources", "/v2/api-docs", "/actuator/"};

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o instanceof ReturnT) {
            return o;
        }
        String path = serverHttpRequest.getURI().getPath();
        if (Arrays.stream(excludePaths).anyMatch(path::startsWith)) {
            return o;
        }
        return ReturnT.success(o);
    }
}
