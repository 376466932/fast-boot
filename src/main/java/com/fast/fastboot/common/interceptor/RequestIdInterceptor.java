package com.fast.fastboot.common.interceptor;

import com.fast.fastboot.common.Constants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class RequestIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //generate request id
        String xRequestId = request.getHeader(Constants.X_REQUEST_ID_HEADER_NAME);
        if (!StringUtils.hasText(xRequestId)) {
            xRequestId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        //set the request id to response and MDC
        response.setHeader(Constants.X_REQUEST_ID_HEADER_NAME, xRequestId);
        MDC.put(Constants.X_REQUEST_ID_HEADER_NAME, xRequestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
