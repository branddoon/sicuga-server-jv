package com.sicuga.sicugaserver.exception;

import com.sicuga.sicugaserver.dto.GlobalExceptionDTOResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        FieldError fieldError= ex.getFieldErrors()
                .stream().filter(msg -> !Strings.isEmpty(msg.getDefaultMessage()))
                .findFirst()
                .get();
        return new ResponseEntity<>(new GlobalExceptionDTOResponse(fieldError.getDefaultMessage()),HttpStatus.BAD_REQUEST);
    }

}
