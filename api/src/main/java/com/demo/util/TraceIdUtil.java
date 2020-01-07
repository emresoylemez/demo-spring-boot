package com.demo.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TraceIdUtil {
    public static final String TRACE_ID = "TraceId";
    public static final String TRACE_ID_PREFIX = "traceid:";

    public String getTraceId() {
        return TRACE_ID_PREFIX + UUID.randomUUID().toString();
    }
}
