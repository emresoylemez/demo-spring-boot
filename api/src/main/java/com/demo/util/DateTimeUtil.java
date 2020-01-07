package com.demo.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public String getAsUTCTimeStamp(final LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
