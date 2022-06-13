package com.cognizant.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class has the endpoint that can retrieve employee details. It will sort
 * and filter the results based on the request parameters specified
 * 
 * @author cheef
 *
 */
@CrossOrigin("http://localhost:4200")
@RestController
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeRepository repository;

	/**
	 * This method will find the employee details that have salary between minSalary
	 * and maxSalary and return the results accordingly based on the value of
	 * offset,limit and sort
	 * 
	 * @param minSalary
	 * @param maxSalary
	 * @param offset
	 * @param limit
	 * @param sort
	 * @return
	 */
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(@RequestParam double minSalary, @RequestParam double maxSalary,
			@RequestParam int offset, @RequestParam int limit, @RequestParam String sort) {
		String newSort = sort.substring(1);
		if (newSort.equals("id")) {
			if (sort.startsWith("-")) {
				List<Employee> employees = repository.findBySalaryBetweenOrderByIdDesc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			} else {
				List<Employee> employees = repository.findBySalaryBetweenOrderByIdAsc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			}
		}

		if (newSort.equals("name")) {
			if (sort.startsWith("-")) {
				List<Employee> employees = repository.findBySalaryBetweenOrderByNameDesc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			} else {
				List<Employee> employees = repository.findBySalaryBetweenOrderByNameAsc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			}
		}

		if (newSort.equals("login")) {
			if (sort.startsWith("-")) {
				List<Employee> employees = repository.findBySalaryBetweenOrderByLoginDesc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			} else {
				List<Employee> employees = repository.findBySalaryBetweenOrderByLoginAsc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			}
		}

		if (newSort.equals("salary")) {
			if (sort.startsWith("-")) {
				List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryDesc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			} else {
				List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryAsc(minSalary, maxSalary);
				return new ResponseEntity<List<Employee>>(employees.subList(offset, Math.min(employees.size(), limit)),
						HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
