package com.cognizant.dashboard.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cognizant.dashboard.entity.Employee;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository repository;

	@BeforeAll
	public void setup() {
		repository.deleteAll();
		Employee employee1 = new Employee(1, "harry" , "hpotter" , 1000);
		Employee employee2 = new Employee(2, "malfoy" , "dmalfoy" , 2000);
		Employee employee3 = new Employee(3, "ron" , "rweasley" , 3000);
		repository.save(employee1);
		repository.save(employee2);
		
		
	}
	
	@Test
	public void findByIdtest() {
		assertEquals("hpotter", repository.findById(1).get().getLogin());;
	}
	
	@Test
	public void findBySalaryBetweenOrderByIdAscTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByIdAsc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("malfoy", employees.get(1).getName());
	}
	
	@Test
	public void findBySalaryBetweenOrderByIdDescTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByIdDesc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("harry", employees.get(1).getName());
	}
	
	@Test
	public void findBySalaryBetweenOrderByNameAscTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByNameAsc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("harry", employees.get(0).getName());
	}
	
	@Test
	public void findBySalaryBetweenOrderByNameDescTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByNameDesc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("malfoy", employees.get(0).getName());
	}
	
	@Test
	public void findBySalaryBetweenOrderByLoginAscTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByLoginAsc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("dmalfoy", employees.get(0).getLogin());
	}
	
	@Test
	public void findBySalaryBetweenOrderByLoginDescTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderByLoginDesc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("hpotter", employees.get(0).getLogin());
	}
	
	@Test
	public void findBySalaryBetweenOrderBySalaryAscTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryAsc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("hpotter", employees.get(0).getLogin());
	}

	@Test
	public void findBySalaryBetweenOrderBySalaryDescTest() {
		List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryDesc(1000, 2000);
		assertEquals(2, employees.size());
		assertEquals("dmalfoy", employees.get(0).getLogin());
	}
}
