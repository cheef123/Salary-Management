package com.cognizant.upload.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	void getAllEmployeestest() {
		Employee employee = new Employee("e001", "harryp", "potter", 1000);
		Employee employee1 = new Employee("e002", "p", "potter", 2000);
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee,employee1));

		when(employeeRepository.findAll()).thenReturn(employees);
		System.out.println(employeeService.getAllEmployees());
		assertEquals(employeeService.getAllEmployees().size(),employeeRepository.findAll().size());
	}

}
