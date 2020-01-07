package com.demo.logging;

import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.demo.logging.Markers.EXCEPTION;
import static com.demo.logging.Markers.REQUEST;
import static com.demo.logging.Markers.RESPONSE;
import static feign.Util.UTF_8;
import static feign.Util.decodeOrDefault;
import static feign.Util.valuesOrEmpty;

/**
 * This class created based on the Slf4JLogger to customize logging.
 * Logs to SLF4J at the info level
 * {@link feign.Logger}.
 */
@Component
@Slf4j
public class CustomSl4jFeingLogger extends feign.Logger {

    @Autowired
    private DataMasker dataMasker;

    @Override
    protected void logRequest(final String configKey, final Level logLevel, final Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodTag(configKey));
        sb.append(String.format(" ---> %s %s HTTP/1.1\n", request.httpMethod().name(), request.url()));
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : request.headers().keySet()) {
                for (String value : valuesOrEmpty(request.headers(), field)) {

                    sb.append(String.format("%s: %s\n", field, dataMasker.maskHeaderValue(field, value)));
                }
            }

            int bodyLength = 0;
            if (request.body() != null) {
                bodyLength = request.body().length;
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    String bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
                    sb.append(String.format("%s\n", bodyText != null ? dataMasker.maskJsonFields(bodyText) : "Binary data"));
                }
            }
            sb.append(String.format("---> END HTTP (%s-byte body)", bodyLength));
        }

        LOGGER.info(REQUEST, sb.toString());
    }

    @Override
    protected Response logAndRebufferResponse(final String configKey,
                                              final Level logLevel,
                                              final Response response,
                                              final long elapsedTime)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason()
                : "";

        int status = response.status();
        sb.append(methodTag(configKey));
        sb.append(String.format(" <--- HTTP/1.1 %s%s (%sms)\n", status, reason, elapsedTime));

        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : response.headers().keySet()) {
                for (String value : valuesOrEmpty(response.headers(), field)) {
                    sb.append(String.format("%s: %s\n", field, value));
                }
            }

            int bodyLength = 0;
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    String binaryData = decodeOrDefault(bodyData, UTF_8, "Binary data");
                    sb.append(String.format("%s\n", dataMasker.maskJsonFields(binaryData)));
                }
                sb.append(String.format("<--- END HTTP (%s-byte body)\n", bodyLength));
                LOGGER.info(RESPONSE, sb.toString());
                return response.toBuilder().body(bodyData).build();
            } else {
                sb.append(String.format("<--- END HTTP (%s-byte body)", bodyLength));
            }
        }

        LOGGER.info(RESPONSE, sb.toString());
        return response;
    }

    @Override
    protected IOException logIOException(final String configKey,
                                         final Level logLevel,
                                         final IOException ioe,
                                         final long elapsedTime) {

        StringBuilder sb = new StringBuilder();
        sb.append(methodTag(configKey));

        sb.append(String.format("<--- ERROR %s: %s (%sms)\n", ioe.getClass().getSimpleName(), ioe.getMessage(), elapsedTime));
        if (logLevel.ordinal() >= Level.FULL.ordinal()) {
            StringWriter sw = new StringWriter();
            ioe.printStackTrace(new PrintWriter(sw));
            sb.append(String.format("%s\n", sw.toString()));
            sb.append("<--- END ERROR");
        }

        LOGGER.error(EXCEPTION, sb.toString());
        return ioe;
    }

    @Override
    protected void log(final String configKey, final String format, final Object... args) {
        LOGGER.info(String.format(methodTag(configKey) + format, args));
    }
}

