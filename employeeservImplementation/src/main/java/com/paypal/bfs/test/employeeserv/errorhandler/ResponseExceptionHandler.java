package com.paypal.bfs.test.employeeserv.errorhandler;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    private ResponseEntity<EmployeeServiceErrorResponse> handleNotFound(EmployeeNotFoundException ex, WebRequest request) {
        EmployeeServiceErrorResponse response = EmployeeServiceErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .error(ex.getCause())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmployeeDuplicityException.class)
    private ResponseEntity<EmployeeServiceErrorResponse> handleDuplicity(EmployeeDuplicityException ex, WebRequest request) {
        EmployeeServiceErrorResponse response = EmployeeServiceErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getCause())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
