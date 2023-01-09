package com.sicuga.sicugaserver.exception;

import com.sicuga.sicugaserver.dto.GlobalExceptionDTOResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> globalException(GlobalException e) {
        log.error("error occurred {}", e.getCustomMessage());
        return new ResponseEntity<>(new GlobalExceptionDTOResponse(e.getCustomMessage()), e.getHttpStatus());
    }



}