package com.cognizant.upload.controller;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

//since webmvctest does not pick up @service, we have to provide the service as a @mockbean
@WebMvcTest(EmployeeController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class EmployeeControllerTest {

	// use mockbean to mock away the business logic, since we do not want to test
	// integration between controller and business logic, but between controller
	// and http layer
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
	
	// Need to name the file as "file"
	// since we specified it as "file" in our controller
	// file type needs to be text/csv
    MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "some xml".getBytes());


	/**
	 * Test if mockMvc is created
	 */
	@Test
	void shouldCreateMockMvc() {
		assertNotNull(mockMvc);
	}

	@Test
	void shouldReturnAllEmployees() throws Exception {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
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
		mockMvc.perform(get("/employees")).andExpect(status().isForbidden())
				.andExpect(jsonPath("$.message", is("Database is empty!"))).andDo(print());

	}

	@Test
	public void throwConcurrentUploadExceptiontest()
			throws Exception {
		doThrow(ConcurrentUploadException.class).when(employeeService).save(file);
//		assertThrows(ConcurrentUploadException.class, () -> employeeService.save(file));
		mockMvc.perform(multipart("/upload").file(file)) 
		.andExpect(status().isForbidden())
		.andExpect(jsonPath("$.reason", is("Concurrent uploads!")))
		.andDo(print());
	}
	
	@Test
	public void throwColumnSizeExceptiontest()
			throws Exception {
		doThrow(ColumnSizeException.class).when(employeeService).save(file);
		assertThrows(ColumnSizeException.class, () -> employeeService.save(file));
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Wrong column size!")))
			.andDo(print());
	}

}
