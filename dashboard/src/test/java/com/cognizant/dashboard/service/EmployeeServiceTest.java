package com.cognizant.dashboard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository repository;
	
	@Autowired
	private EmployeeService service;

	Employee employee = new Employee(1, "harryp", "potter", 1000);
	Employee employee1 = new Employee(2, "p", "pitter", 2000);
	List<Employee> employees = new ArrayList<>(Arrays.asList(employee, employee1));


	@Test
	void deleteEmployeeTest() {
		doNothing().when(repository).delete(employee);
		service.deleteEmployee(employee);
	}
	
	@Test
	void findAllEmployeesTest() {
		when(repository.findAll()).thenReturn(employees);
		assertEquals(2, service.findAllEmployees().size());
		assertEquals("potter", service.findAllEmployees().get(0).getLogin());
	}

	@Test
	void saveEmployeeTest() {
		when(repository.save(Mockito.any(Employee.class))).thenReturn(employee);
		assertEquals("potter", service.saveEmployee(employee).getLogin());
	}
	
	@Test
	void findByEmployeeIdTest() {
		when(repository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(employee));
		assertEquals("potter", service.findEmployeeById(1).get().getLogin());
	}
	
	@Test
	void findBySalaryBetweenOrderBySalaryAscTest() {
		when(repository.findBySalaryBetweenOrderBySalaryAsc(0, 4000)).thenReturn(employees);
		List<Employee> actualList = service.findBySalaryBetweenOrderBySalaryAsc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals(1000, actualList.get(0).getSalary());
	}
	
	@Test
	void findBySalaryBetweenOrderBySalaryDescTest() {
		List<Employee> employeeDesc = new ArrayList<>(Arrays.asList(employee1,employee));
		when(repository.findBySalaryBetweenOrderBySalaryDesc(0, 4000)).thenReturn(employeeDesc);
		List<Employee> actualList = service.findBySalaryBetweenOrderBySalaryDesc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals(2000, actualList.get(0).getSalary());
	}
	
	@Test
	void findBySalaryBetweenOrderByLoginAscTest() {
		List<Employee> employeeDesc = new ArrayList<>(Arrays.asList(employee1,employee));
		when(repository.findBySalaryBetweenOrderByLoginAsc(0, 4000)).thenReturn(employeeDesc);
		List<Employee> actualList = service.findBySalaryBetweenOrderByLoginAsc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals("pitter", actualList.get(0).getLogin());
	}
	
	@Test
	void findBySalaryBetweenOrderByLoginDescTest() {
		when(repository.findBySalaryBetweenOrderByLoginDesc(0, 4000)).thenReturn(employees);
		List<Employee> actualList = service.findBySalaryBetweenOrderByLoginDesc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals("potter", actualList.get(0).getLogin());
	}
	
	@Test
	void findBySalaryBetweenOrderByNameAscTest() {
		when(repository.findBySalaryBetweenOrderByNameAsc(0, 4000)).thenReturn(employees);
		List<Employee> actualList = service.findBySalaryBetweenOrderByNameAsc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals("harryp", actualList.get(0).getName());
	}
	
	@Test
	void findBySalaryBetweenOrderByNameDescTest() {
		List<Employee> employeeDesc = new ArrayList<>(Arrays.asList(employee1,employee));
		when(repository.findBySalaryBetweenOrderByNameDesc(0, 4000)).thenReturn(employeeDesc);
		List<Employee> actualList = service.findBySalaryBetweenOrderByNameDesc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals("p", actualList.get(0).getName());
	}
	
	@Test
	void findBySalaryBetweenOrderByIdAscTest() {
		when(repository.findBySalaryBetweenOrderByIdAsc(0, 4000)).thenReturn(employees);
		List<Employee> actualList = service.findBySalaryBetweenOrderByIdAsc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals(1, actualList.get(0).getId());
	}
	
	@Test
	void findBySalaryBetweenOrderByIdDescTest() {
		List<Employee> employeeDesc = new ArrayList<>(Arrays.asList(employee1,employee));
		when(repository.findBySalaryBetweenOrderByIdDesc(0, 4000)).thenReturn(employeeDesc);
		List<Employee> actualList = service.findBySalaryBetweenOrderByIdDesc(0, 4000, 0, 10);
		assertEquals(2, actualList.size());
		assertEquals(2, actualList.get(0).getId());
	}
	
	@Test
	void correctColumnNameTest() {
		assertTrue(service.correctColumns("+salary"));
		assertTrue(service.correctColumns("-salary"));
		assertTrue(service.correctColumns("+id"));
		assertTrue(service.correctColumns("-id"));
		assertTrue(service.correctColumns("+login"));
		assertTrue(service.correctColumns("-login"));
		assertTrue(service.correctColumns("+name"));
		assertTrue(service.correctColumns("-name"));
	}
	
	@Test
	void wrongColumnNameTest() {
		assertFalse(service.correctColumns("+income"));
	}
	
	@Test
	void getUsersSortByIdAscTest() {
		service.getUsers(0, 4000, 0, 10, "+id");
		verify(repository,times(1)).findBySalaryBetweenOrderByIdAsc(0, 4000);
	}
	
	@Test
	void getUsersSortByIdDescTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "-id");
		verify(repository,times(1)).findBySalaryBetweenOrderByIdDesc(0, 4000);
	}
	
	@Test
	void getUsersSortByNameDescTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "-name");
		verify(repository,times(1)).findBySalaryBetweenOrderByNameDesc(0, 4000);

	}
	
	@Test
	void getUsersSortByNameAscTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "+name");
		verify(repository,times(1)).findBySalaryBetweenOrderByNameAsc(0, 4000);

	}
	
	@Test
	void getUsersSortBySalaryAscTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "+salary");
		verify(repository,times(1)).findBySalaryBetweenOrderBySalaryAsc(0, 4000);
	}
	
	@Test
	void getUsersSortBySalaryDescTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "-salary");
		verify(repository,times(1)).findBySalaryBetweenOrderBySalaryDesc(0, 4000);

	}
	
	@Test
	void getUsersSortByLoginDescTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "-login");
		verify(repository,times(1)).findBySalaryBetweenOrderByLoginDesc(0, 4000);

	}
	
	@Test
	void getUsersSortByLoginAscTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "+login");
		verify(repository,times(1)).findBySalaryBetweenOrderByLoginAsc(0, 4000);

	}
	
	@Test
	void getUsersNullTest() {
		List<Employee> expectedList = service.getUsers(0, 4000, 0, 10, "income");
		assertNull(expectedList);
	}
}
