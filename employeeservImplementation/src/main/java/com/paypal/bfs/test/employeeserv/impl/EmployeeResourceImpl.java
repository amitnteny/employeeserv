package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) throws EmployeeNotFoundException {
        return ResponseEntity.ok().body(employeeService.retrieveEmployeeById(Integer.valueOf(id)));
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) throws EmployeeDuplicityException {
        return ResponseEntity.ok().body(employeeService.createEmployee(employee));
    }
}
