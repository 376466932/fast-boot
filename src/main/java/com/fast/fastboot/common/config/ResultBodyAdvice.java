package com.fast.fastboot.common.config;

import com.fast.fastboot.common.bean.RestResult;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class ResultBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * @return boolean. true：执行函数beforeBodyWrite；否则，不执行
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), RestController.class) || returnType.hasMethodAnnotation(ResponseBody.class);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 避免重复封装响应报文
        if (body instanceof RestResult) {
            return body;
        }
        // 使用约定全局返回格式封装响应报文
        return RestResult.success(body);
    }

}