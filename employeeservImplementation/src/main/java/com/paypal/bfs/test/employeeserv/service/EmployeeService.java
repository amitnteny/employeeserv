package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee) throws EmployeeDuplicityException;

    Employee retrieveEmployeeById(Integer employeeId) throws EmployeeNotFoundException;
}
