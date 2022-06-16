package com.cognizant.upload.controller;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.ConcurrentUploadException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.LoginConflictException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;
import com.cognizant.upload.exception.SalaryFormatException;
import com.cognizant.upload.helper.CSVHelper;
import com.cognizant.upload.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	Employee employee1 = Employee.builder()
			.empId("e001")
			.empLogin("hpotter")
			.empName("harry")
			.empSalary(1000)
			.build();
	
	Employee employee2 = Employee.builder()
			.empId("e002")
			.empLogin("rweasley")
			.empName("ron")
			.empSalary(2000)
			.build();
	
	Employee employee3 = Employee.builder()
			.empId("e003")
			.empLogin("hgranger")
			.empName("hermoine")
			.empSalary(3000)
			.build();

	/**
	 * Test if mockMvc is created
	 */
	@Test
	void shouldCreateMockMvc() {
		assertNotNull(mockMvc);
	}
	
	@Test
	void shouldReturnAllEmployees() throws Exception {
		Employee employee1 = Employee.builder()
				.empId("e001")
				.empLogin("hpotter")
				.empName("harry")
				.empSalary(1000)
				.build();
		
		Employee employee2 = Employee.builder()
				.empId("e002")
				.empLogin("rweasley")
				.empName("ron")
				.empSalary(2000)
				.build();
		
		Employee employee3 = Employee.builder()
				.empId("e003")
				.empLogin("hgranger")
				.empName("hermoine")
				.empSalary(3000)
				.build();
		
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
		when(employeeService.getAllEmployees()).thenReturn(employees);
		mockMvc.perform(get("/employees")) 
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()", is(employees.size())))
			.andDo(print());
	
        }
		
	
	
	@Test
	public void shouldReturnErrorWhenEmpty() throws Exception {
		List<Employee> employees = new ArrayList<>();
		when(employeeService.getAllEmployees()).thenReturn(employees);
		mockMvc.perform(get("/employees"))
		.andExpect(status().isForbidden())
		.andExpect(jsonPath("$.message", is("Database is empty!")))
		.andDo(print());
		
	}
	
	@Test
	public void throwExceptiontest() throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException, ColumnSizeException, EmptyFileException, ConcurrentUploadException, SalaryFormatException {
		byte[] content = null;
		MultipartFile file = new MockMultipartFile("test", content);
		doThrow(ConcurrentUploadException.class).when(employeeService).save(file);
		assertThrows(ConcurrentUploadException.class, ()-> employeeService.save(file));
	}
	
	
		
}
