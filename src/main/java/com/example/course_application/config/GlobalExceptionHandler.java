package com.example.course_application.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.course_application.entity.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ApiResponse<Map<String, String>>> handleAccessDeniedException(AccessDeniedException e) {
        return ApiResponse.buildError("You don't have permission to perform this action.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<ApiResponse<Map<String, String>>> handleBadCredentialsException(
            BadCredentialsException e) {
        return ApiResponse.buildError("Invalid username or password.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiResponse<Map<String, String>>> handleException(
            Exception e) {
        return ApiResponse.buildError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
