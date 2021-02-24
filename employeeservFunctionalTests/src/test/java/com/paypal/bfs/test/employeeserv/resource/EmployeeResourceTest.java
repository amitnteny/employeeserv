package com.paypal.bfs.test.employeeserv.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.paypal.bfs.test.employeeserv.util.DataUtil.createEmployee;
import static com.paypal.bfs.test.employeeserv.util.EmployeeConverterUtil.convertToEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = EmployeeservApplication.class)
@AutoConfigureMockMvc
public class EmployeeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setUp() {
        Employee amit = createEmployee();
        employeeRepository.save(convertToEntity(amit));
        /*Mockito.when(employeeRepository.findByIdempotenceId(amit.getIdempotenceId()))
                .thenReturn(Optional.of(amitEntity));
        Mockito.when(employeeRepository.findById(amit.getId()))
                .thenReturn(Optional.of(amitEntity));*/
    }

    @Test
    public void test_createEmployee_should_return_status_200() throws Exception {
        Employee sumit = createEmployee();
        sumit.setFirstName("Sumit");
        mockMvc.perform(post("/v1/bfs/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(sumit)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void given_employee_is_not_persisted_then_employee_get_by_id_returns_status_404() throws Exception {
        mockMvc.perform(get("/v1/bfs/employees/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void given_employee_amit_is_persisted_then_employee_get_by_id_returns_status_200() throws Exception {
        setUp();
        mockMvc.perform(get("/v1/bfs/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_duplicate_entry_in_createEmployee_returns_status_409_conflict() throws Exception {
        Employee john = createEmployee();
        john.setFirstName("john");
        mockMvc.perform(post("/v1/bfs/employee")
                .content(new ObjectMapper().writeValueAsString(john))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(post("/v1/bfs/employee")
                .content(new ObjectMapper().writeValueAsString(john))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}
