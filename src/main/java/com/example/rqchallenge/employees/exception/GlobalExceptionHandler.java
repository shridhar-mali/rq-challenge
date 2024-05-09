package com.example.rqchallenge.employees.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessServiceException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ErrorResponse handleBusinessServiceException(BusinessServiceException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRestClientException(RestClientException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUncaughtException(Exception ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(),
                request.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, new ErrorResponse(ex.getMessage(),
                request.getDescription(false)), headers, status, request);
    }
}
