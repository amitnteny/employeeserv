package com.paypal.bfs.test.employeeserv.util;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeConverterUtil {

    public static EmployeeEntity convertToEntity(Employee employee) {
        Address address = employee.getAddress();
        return EmployeeEntity.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .dateOfBirth(toLocalDate(employee.getDateOfBirth()))
                .address(AddressEntity.builder()
                        .line1(address.getLine1())
                        .line2(address.getLine2())
                        .city(address.getCity())
                        .state(address.getState())
                        .country(address.getCountry())
                        .zipCode(address.getZipCode())
                        .build())
                .build();
    }

    public static LocalDate toLocalDate(String dateOfBirth) throws RuntimeException {
        return LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Employee convertToDto(EmployeeEntity employeeEntity) {
        AddressEntity address = employeeEntity.getAddress();
        Employee employee = new Employee();
        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setId(employeeEntity.getId());
        employee.setDateOfBirth(employeeEntity.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        employee.setAddress(toAddressDto(employeeEntity.getAddress()));
        return employee;
    }

    private static Address toAddressDto(AddressEntity addressEntity) {
        Address address = new Address();
        address.setId(addressEntity.getId());
        address.setLine1(addressEntity.getLine1());
        address.setLine2(addressEntity.getLine2());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        address.setCountry(addressEntity.getCountry());
        address.setZipCode(addressEntity.getZipCode());
        return address;
    }

    private static String toDateString(LocalDate dateOfBirth) {
        return dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
