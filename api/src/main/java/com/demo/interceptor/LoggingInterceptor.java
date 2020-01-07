package com.demo.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.demo.logging.Markers.REQUEST_COMPLETED;
import static com.demo.logging.Markers.REQUEST_COMPLETED_WITH_EXCEPTION;
import static com.demo.logging.Markers.REQUEST_RECEIVED;

@Slf4j
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        LOGGER.info(REQUEST_RECEIVED, "method: {}, url:{}", request.getMethod(), request.getRequestURL());
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        if (ex != null) {
            LOGGER.info(REQUEST_COMPLETED_WITH_EXCEPTION, "[exception: {}]", ex);

        } else {
            LOGGER.info(REQUEST_COMPLETED, "");
        }
    }
}