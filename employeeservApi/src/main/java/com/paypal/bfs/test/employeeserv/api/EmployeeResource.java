package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Interface for employee resource operations.
 */
@Valid
@RequestMapping("/v1/bfs")
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @GetMapping("/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") @NotNull @NotBlank String id) throws EmployeeNotFoundException;

    /**
     * @param employee employee details
     * @return persisted employee
     * @throws EmployeeDuplicityException on duplicate element
     */
    @PostMapping("/employee")
    ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee) throws EmployeeDuplicityException;

}


