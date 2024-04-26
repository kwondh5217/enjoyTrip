package com.example.enjoytrip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private HttpStatus status;
    private String errorMessage;
}
