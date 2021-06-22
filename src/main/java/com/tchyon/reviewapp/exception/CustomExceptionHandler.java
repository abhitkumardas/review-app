package com.tchyon.reviewapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends Exception {

    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.error("Error Occurred in Class " + ex.getClass().getName() + " ", ex);
        String message;
        if(ex.getMessage().isEmpty()){
            message = "Oops Something went wrong. Please contact our support.";
        }else {
            message = ex.getMessage();
        }
        String stackTrace = ex.fillInStackTrace().toString();
        return new ResponseEntity<Object>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
