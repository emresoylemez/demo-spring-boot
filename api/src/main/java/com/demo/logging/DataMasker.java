package com.demo.logging;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataMasker {

    private static final String HEADER_REGEX = "(Authorization)";
    private static final String JSON_REGEX = "\"(authorisation|password|PAREQ|pareq|pares|accessToken|refreshToken|customerAccessToken)\":\".*?\"(?<!\\\\.)";

    public String maskJsonFields(final String jsonString) {
        return Optional.ofNullable(jsonString)
                .map(x -> jsonString.replaceAll(JSON_REGEX, "\"$1\":\"XXX\""))
                .orElse("null");
    }

    public String maskHeaderValue(final String header, final String value) {
        return Optional.ofNullable(value)
                .map(x -> header.matches(HEADER_REGEX) ? "XXX" : x)
                .orElse("null");
    }

}
