package com.cognizant.dashboard.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.exception.ResourceNotFoundException;
import com.cognizant.dashboard.repository.EmployeeRepository;

@SpringBootTest
class EmployeeControllerTest {

	@Mock
	private EmployeeRepository repository;

	@Autowired
	private EmployeeController controller;
	
	Employee employee1 = new Employee(1, "Ryl", "Rylee", 0);
	Employee employee2 = new Employee(2, "Ric", "Richards", 200);
	Employee employee3 = new Employee(3, "Law", "Lawrence", 400);

	@Test
	void sortIdDesctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee3, employee2, employee1));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "-id");
		Mockito.when(repository.findBySalaryBetweenOrderByIdDesc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByIdDesc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortIdAsctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "+id");
		Mockito.when(repository.findBySalaryBetweenOrderByIdAsc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByIdAsc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortNameAsctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee3, employee2, employee1));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "+name");
		Mockito.when(repository.findBySalaryBetweenOrderByNameAsc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByNameAsc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortNameDesctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "-name");
		Mockito.when(repository.findBySalaryBetweenOrderByNameDesc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByNameDesc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortLoginAsctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee3, employee2, employee1));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "+login");
		Mockito.when(repository.findBySalaryBetweenOrderByLoginAsc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByLoginAsc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortLoginDesctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "-login");
		Mockito.when(repository.findBySalaryBetweenOrderByLoginDesc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderByLoginDesc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortSalaryAsctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "+salary");
		Mockito.when(repository.findBySalaryBetweenOrderBySalaryAsc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderBySalaryAsc(0, 400), responseEntity.getBody());
	}
	
	@Test
	void sortSalaryDesctest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee3, employee2, employee1));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, "-salary");
		Mockito.when(repository.findBySalaryBetweenOrderBySalaryDesc(0, 400)).thenReturn(employees);
		assertEquals(repository.findBySalaryBetweenOrderBySalaryDesc(0, 400), responseEntity.getBody());
	}

	
	@Test
	void internalErrortest() {
		List<Employee> employees = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		ResponseEntity<?> responseEntity = controller.getUsers(0, 400, 0, 5, " ");
		Mockito.when(repository.findBySalaryBetweenOrderByIdDesc(0, 400)).thenReturn(employees);
		assertEquals("Internal Server Error", responseEntity.getBody());
	}
	
	@Test
	void updateEmployeeTest() throws ResourceNotFoundException {
		Employee employee = new Employee(4, "Lucifer", "Lucy", 1000);
		ResponseEntity<?> responseEntity = controller.updateEmployees(4, employee);
		Mockito.when(repository.findById(4)).thenReturn(Optional.of(employee));
		assertEquals(employee, responseEntity.getBody());
	}
}
