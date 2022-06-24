package com.cognizant.dashboard.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.exception.ResourceNotFoundException;
import com.cognizant.dashboard.repository.EmployeeRepository;
import com.cognizant.dashboard.service.EmployeeService;

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

	@Autowired
	private EmployeeService service;

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

		return service.getUsers(minSalary, maxSalary, offset, limit, sort);
	}

	/**
	 * This method will find employee object by id and update the respective fields.
	 * Throws ResourceNotFoundException if object is not found
	 * 
	 * @param id
	 * @param employeeDetails
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> updateEmployees(@PathVariable int id, @RequestBody Employee employeeDetails)
			throws ResourceNotFoundException {

		Employee employee = service.findEmployeeById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

		employee.setLogin(employeeDetails.getLogin());
		employee.setName(employeeDetails.getName());
		employee.setSalary(employeeDetails.getSalary());

		return new ResponseEntity<Employee>(repository.save(employee), HttpStatus.OK);

	}

	/**
	 * This method will find and delete the employee object based on id. Throws
	 * ResourceNotFoundException if employee object is not found
	 * 
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable int id) throws ResourceNotFoundException {

		Employee employee = service.findEmployeeById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
		service.deleteEmployee(employee);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/usersAll")
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<List<Employee>>(service.findAllEmployees(), HttpStatus.OK);
	}

}
