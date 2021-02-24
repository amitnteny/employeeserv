package com.paypal.bfs.test.employeeserv.service.impl;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.util.EmployeeConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.util.EmployeeConverterUtil.*;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) throws EmployeeDuplicityException {
        log.info("Checking if employee={} already present in the database.", employee);
        Optional<EmployeeEntity> entityOptional = employeeRepository.findByFirstNameAndLastNameAndDateOfBirth(
                employee.getFirstName(), employee.getLastName(), toLocalDate(employee.getDateOfBirth()));
        if (entityOptional.isPresent()) {
            log.error("Employee={} is duplicate. Throwing EmployeeDuplicityException.", employee);
            EmployeeEntity entity = entityOptional.get();
            throw new EmployeeDuplicityException("Employee already present in the database.");
        }
        log.info("Saving employee={} in database.", employee);
        EmployeeEntity employeeEntity = convertToEntity(employee);
        return convertToDto(employeeRepository.save(employeeEntity));
    }

    @Override
    public Employee retrieveEmployeeById(Integer employeeId) throws EmployeeNotFoundException {
        log.info("Retrieving employee with employeeId={} from database.", employeeId);
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(employeeId);
        return employeeEntityOptional.map(EmployeeConverterUtil::convertToDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with employee_id = " + employeeId + " not found in the database."));
    }
}
