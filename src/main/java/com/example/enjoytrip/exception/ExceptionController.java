package com.example.enjoytrip.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e){
        ErrorResponse response = new ErrorResponse(e.getErrorCode().getHttpStatus(),
                e.getErrorCode().getErrorMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

}
