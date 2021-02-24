package com.paypal.bfs.test.employeeserv.errorhandler;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeServiceErrorResponse {
    private int status;
    private Throwable error;
    private String message;
    private LocalDateTime timestamp;
}
