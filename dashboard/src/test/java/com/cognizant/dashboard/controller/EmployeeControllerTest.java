package com.cognizant.dashboard.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.exception.EmployeeNotFoundException;
import com.cognizant.dashboard.repository.EmployeeRepository;
import com.cognizant.dashboard.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	private EmployeeService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	Employee employee1 = new Employee(1, "Ry", "Rylee", 0);
	Employee employee2 = new Employee(2, "Ri", "Richards", 200);
	Employee employee3 = new Employee(3, "Law", "Lawrence", 400);
	
	@Test
	public void shouldCreateMockMvcTest() {
		assertNotNull(mockMvc);
	}
	
	@Test
	public void getAllEmployeesTest() throws Exception {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
		when(service.findAllEmployees()).thenReturn(employees);
		mockMvc.perform(get("/usersAll"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()", is(employees.size())))
			.andDo(print());
	}
	
	@Test
	public void deleteEmployeesTest() throws Exception {
		when(service.findEmployeeById(1)).thenReturn(Optional.of(employee1));
		mockMvc.perform(delete("/users/{id}","1"))
			.andExpect(status().isNoContent())
			.andDo(print()); 
	}
	
	@Test
	public void deleteEmployeeNotFoundExceptionTest() throws Exception {
		// return Optional.empty to trigger orelsethrow
		when(service.findEmployeeById(1)).thenReturn(Optional.empty());
		mockMvc.perform(delete("/users/{id}","1"))
		.andExpect(status().isExpectationFailed())
		.andExpect(jsonPath("$.reason", is("Resource not found!")))
		.andDo(print());
	}
	
	@Test
	public void updateEmployeeTest() throws Exception {
		when(service.findEmployeeById(1)).thenReturn(Optional.of(employee1));
		Employee updateEmployee = new Employee(1, "Rice", "Ricelee", 10);
		mockMvc.perform(patch("/users/{id}","1")
                .contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateEmployee)))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void updateEmployeeNotFoundExceptionTest() throws Exception{
		when(service.findEmployeeById(1)).thenReturn(Optional.empty());
		Employee updateEmployee = new Employee(1, "Rice", "Ricelee", 10);
		mockMvc.perform(patch("/users/{id}","1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateEmployee)))
			.andExpect(status().isExpectationFailed())
			.andExpect(jsonPath("$.reason", is("Resource not found!")))
			.andDo(print());
	}
	
	@Test
	public void getUsersFailTest() throws Exception {
		when(service.correctColumns(Mockito.any(String.class))).thenReturn(false);
		mockMvc.perform(get("/users")
			.param("minSalary","0")
			.param("maxSalary", "4000")
			.param("offset", "0")
			.param("limit", "5")
			.param("sort", "-salary"))
			.andExpect(status().isInternalServerError())
			.andExpect(jsonPath("$", is("Internal Server Error")))
			.andDo(print());
		
	}
	
	@Test
	public void getUsersPassTest() throws Exception {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
		when(service.correctColumns(Mockito.any(String.class))).thenReturn(true);
		mockMvc.perform(get("/users")
			.param("minSalary","0")
			.param("maxSalary", "4000")
			.param("offset", "0")
			.param("limit", "5")
			.param("sort", "-salary"))
			.andExpect(status().isOk())
			.andDo(print());
		
	}

}
