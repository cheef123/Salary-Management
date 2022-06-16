package com.cognizant.upload.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.ConcurrentFlag;
import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.ConcurrentUploadException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.LoginConflictException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;
import com.cognizant.upload.exception.SalaryFormatException;
import com.cognizant.upload.repository.ConcurrentFlagRepository;
import com.cognizant.upload.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Mock
	private ConcurrentFlagRepository flagRepository;
	

	
	byte[] content = null;
	MultipartFile file = new MockMultipartFile("test", content);

	
	@Test
	public void getAllEmployeestest() {
		Employee employee = new Employee("e001", "harryp", "potter", 1000);
		Employee employee1 = new Employee("e002", "p", "potter", 2000);
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee,employee1));

		when(employeeRepository.findAll()).thenReturn(employees);
		System.out.println(employeeService.getAllEmployees());
		assertEquals(employeeService.getAllEmployees().size(),employeeRepository.findAll().size());
	}
	
	@Test
	public void saveTest() {
		byte[] content = null;
		Employee employee = new Employee("e001", "harryp", "potter", 1000);
		Employee employee1 = new Employee("e0012", "harryp", "potter", 1000);
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee));
		List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1));
		when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
		when(employeeRepository.findByEmpLogin("e001")).thenReturn(employees1);
		assertThrows(ColumnSizeException.class, ()-> employeeService.save(file) );
		
	}
	
	@Test
	public void columnSizeExceptionTest() {
		assertThrows(ColumnSizeException.class, ()->employeeService.save(file));
	}
	

}
