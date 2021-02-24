package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.exception.EmployeeDuplicityException;
import com.paypal.bfs.test.employeeserv.api.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.util.DataUtil.createEmployee;
import static com.paypal.bfs.test.employeeserv.util.EmployeeConverterUtil.convertToEntity;
import static com.paypal.bfs.test.employeeserv.util.EmployeeConverterUtil.toLocalDate;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
        Employee amit = createEmployee();
        EmployeeEntity amitEntity = convertToEntity(amit);
        amitEntity.setId(1);
        amitEntity.getAddress().setId(2);
        Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(amitEntity));
        Mockito.when(employeeRepository.save(amitEntity)).thenReturn(amitEntity);
    }

    @Test
    public void given_employee_id_1_then_retrieveEmployeeById_should_return_amitDto() throws Exception {
        Employee amit = createEmployee();
        EmployeeEntity entity = convertToEntity(amit);
        Employee persistedEmployee = employeeService.retrieveEmployeeById(1);
        Assert.assertNotNull(persistedEmployee);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void should_throw_exception_for_retrieving_entry_not_present_in_db() throws Exception {
        employeeService.retrieveEmployeeById(500);
    }

    @Test(expected = EmployeeDuplicityException.class)
    public void should_throw_exception_for_duplicate_employee_creation() throws Exception {
        Employee employeeDto = createEmployee();
        EmployeeEntity amitEntity = convertToEntity(employeeDto);
        Mockito.when(employeeRepository.findByFirstNameAndLastNameAndDateOfBirth(
                employeeDto.getFirstName(), employeeDto.getLastName(), toLocalDate(employeeDto.getDateOfBirth())))
                .thenReturn(Optional.of(amitEntity));
        employeeService.createEmployee(employeeDto);
        employeeService.createEmployee(employeeDto);
    }
}
