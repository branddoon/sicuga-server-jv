package com.sicuga.sicugaserver.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<String> globalException(GlobalException e) {
        logger.error("error occurred {}", e.getCustomMessage());
        return new ResponseEntity<>("Something happened: " + e.getCustomMessage(), e.getHttpStatus());
    }
}