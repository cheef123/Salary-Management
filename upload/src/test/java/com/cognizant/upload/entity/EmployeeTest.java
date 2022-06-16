package com.cognizant.upload.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
	
	private Employee employee;

	@BeforeEach
	void setUp() throws Exception {
		employee = new Employee("e001", "hpotter", "Harry", 1000);
	}

	@Test
	public void testGetSetEmpId() {
		employee.setEmpId("e002");
		assertEquals("e002", employee.getEmpId());
	}
	
	@Test
	public void testGetSetLogin() {
		employee.setEmpLogin("hermG");
		assertEquals("hermG", employee.getEmpLogin());
	}
	
	@Test
	public void testGetSetName() {
		employee.setEmpName("hermoine");
		assertEquals("hermoine", employee.getEmpName());
	}
	
	@Test
	public void testGetSetSalary() {
		employee.setEmpSalary(123.34);
		assertEquals(123.34, employee.getEmpSalary());
	}

}
