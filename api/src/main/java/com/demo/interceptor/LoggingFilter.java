package com.demo.interceptor;

import com.demo.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.demo.util.TraceIdUtil.TRACE_ID;

@WebFilter(urlPatterns = "/api/*")
public class LoggingFilter implements javax.servlet.Filter {

    @Autowired
    TraceIdUtil traceIDUtil;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);

        String traceId = req.getHeader(TRACE_ID);

        if (traceId == null || traceId.isEmpty()) {
            traceId = traceIDUtil.getTraceId();
            mutableRequest.putHeader("TraceId", traceId);
        }

        MDC.put(TRACE_ID, traceId);

        chain.doFilter(mutableRequest, response);
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }
}