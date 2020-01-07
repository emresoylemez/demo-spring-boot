package com.demo.exception.handler;

import com.demo.contract.model.ErrorMessage;
import com.demo.exception.DuplicateItemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class DemoExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({DuplicateItemException.class})
    public ResponseEntity<ErrorMessage> handleDuplicates(final DuplicateItemException e) {

        LOGGER.error("Duplicate record {}", e.getItem());
        return new ResponseEntity(new ErrorMessage("Duplicate record."), HttpStatus.BAD_REQUEST);
    }
}