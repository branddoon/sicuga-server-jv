package com.sicuga.sicugaserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class GlobalException extends RuntimeException{

    private String customMessage;
    
    private HttpStatus httpStatus;

}
