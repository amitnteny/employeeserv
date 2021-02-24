package com.paypal.bfs.test.employeeserv.util;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

public class DataUtil {
    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Amit");
        employee.setLastName("Kumar");
        employee.setDateOfBirth("01/01/1950");
        employee.setAddress(createAddress());
        return employee;
    }

    public static Address createAddress() {
        Address address = new Address();
        address.setLine1("BlaBla");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setZipCode("560001");
        return address;
    }
}
