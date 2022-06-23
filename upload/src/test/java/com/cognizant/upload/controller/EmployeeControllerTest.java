package com.cognizant.upload.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

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
class EmployeeControllerTest {

	// use mockbean to mock away the business logic, since we do not want to test
	// integration between controller and business logic, but between controller
	// and http layer
	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private CSVHelper csvHelper;

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
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
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
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(ColumnSizeException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Wrong column size!")))
			.andDo(print());
	}
	
	
	@Test
	public void EmptyFileExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(EmptyFileException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Empty File!")))
			.andDo(print());
	}
	
	@Test
	public void IllegalArgumentExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(IllegalArgumentException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$.reason", is("Illegal Argument!")))
			.andDo(print());
	}
	
	@Test
	public void NonUniqueIdExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(NonUniqueIdException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$.reason", is("Id must be unique! Cannot be repeated in another row!")))
			.andDo(print());
	}
	
	@Test
	public void NonUniqueLoginExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(NonUniqueLoginException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$.reason", is("Login details must be unique! Cannot be repeated in another row!")))
			.andDo(print());
	}
	
	@Test
	public void NullPointerExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(NullPointerException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$.reason", is("All columns must be filled!")))
			.andDo(print());
	}
	
	@Test
	public void LoginConflictExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(LoginConflictException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Login conflict! Consider complex use case if necessary!")))
			.andDo(print());
	}
	
	@Test
	public void SalaryFormatExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(SalaryFormatException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Incorrectly formatted salary!")))
			.andDo(print());
	}
	
	@Test
	public void NegativeSalaryExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(NegativeSalaryException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isForbidden())
			.andExpect(jsonPath("$.reason", is("Salary value provided should not be negative!")))
			.andDo(print());
	}
	
	@Test
	public void anyOtherNonCustomExceptiontest()
			throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doThrow(RuntimeException.class).when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file)) 
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$", is("Upload failed: filename.csv")))
			.andDo(print());
	}
	
	@Test 
	public void fileUploadSuccessTest() throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(true);
		doNothing().when(employeeService).save(file);
		mockMvc.perform(multipart("/upload").file(file))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message", is("File uploaded successfully: filename.csv")))
			.andDo(print());
		
		
	}
	
	@Test
	public void noCSVFormatTest() throws Exception {
		when(employeeService.hasCSVFormat(file)).thenReturn(false);
		mockMvc.perform(multipart("/upload").file(file))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$", is("File format should be in csv!")))
		.andDo(print());
	}
	
	@Test
	public void exceptionWhenGetAllEmployeesTest() throws Exception {
		when(employeeService.getAllEmployees()).thenThrow(RuntimeException.class);
		mockMvc.perform(get("/employees"))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("$", is("Internal server error!")))
		.andDo(print());
	}

}
