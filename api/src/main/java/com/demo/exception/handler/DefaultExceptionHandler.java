package com.demo.exception.handler;

import com.demo.contract.enums.ErrorCode;
import com.demo.exception.resource.ErrorResource;
import com.demo.exception.resource.FieldErrorResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                               final HttpHeaders headers,
                                                               final HttpStatus status,
                                                               final WebRequest request) {

        final ErrorResource error = new ErrorResource(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_REQUEST, "Validation failed for fields");
        final List<FieldErrorResource> fieldErrorResources = Optional.ofNullable(ex.getBindingResult())
                .map(Errors::getFieldErrors)
                .map(List::stream)
                .orElse(Stream.empty())
                .map(FieldErrorResource::of)
                .collect(Collectors.toList());

        error.setFieldErrors(fieldErrorResources);

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }




    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUncaughtException(final Exception ex,
                                                          final WebRequest request) {

        LOGGER.error("Internal Server Error", ex);
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return this.getErrorResource(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN_ERROR, message);
    }

    private ResponseEntity<Object> getErrorResource(final Exception ex,
                                                    final WebRequest request,
                                                    final HttpStatus httpStatus,
                                                    final ErrorCode errorCode,
                                                    final String message) {

        ErrorResource error = new ErrorResource(httpStatus.value(), errorCode, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, error, headers, httpStatus, request);
    }
}