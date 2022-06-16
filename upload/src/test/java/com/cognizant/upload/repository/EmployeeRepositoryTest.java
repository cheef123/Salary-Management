package com.cognizant.upload.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cognizant.upload.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository repository;
	
	@Test
	public void findByEmpLogintest() {
		
		Employee employee = Employee.builder()
				.empId("e001")
				.empLogin("hpotter")
				.empName("harry")
				.empSalary(1000)
				.build();
		
		repository.save(employee);
		List<Employee> result = repository.findByEmpLogin("hpotter");
		String login = result.get(0).getEmpLogin();
		assertThat(repository.findByEmpLogin("hpotter").get(0).getEmpId()).isEqualTo("e001");
		
	}

}
