package com.cognizant.dashboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.dashboard.entity.Employee;
import com.cognizant.dashboard.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public List<Employee> getUsers(double minSalary, double maxSalary, int offset, int limit, String sort) {
		if (sort.substring(1).equals("id")) {
			return sort.startsWith("-") ? findBySalaryBetweenOrderByIdDesc(minSalary, maxSalary, offset, limit)
					: findBySalaryBetweenOrderByIdAsc(minSalary, maxSalary, offset, limit);
		}

		if (sort.substring(1).equals("name")) {
			return sort.startsWith("-") ? findBySalaryBetweenOrderByNameDesc(minSalary, maxSalary, offset, limit)
					: findBySalaryBetweenOrderByNameAsc(minSalary, maxSalary, offset, limit);
		}

		if (sort.substring(1).equals("login")) {
			return sort.startsWith("-") ? findBySalaryBetweenOrderByLoginDesc(minSalary, maxSalary, offset, limit)
					: findBySalaryBetweenOrderByLoginAsc(minSalary, maxSalary, offset, limit);
		}

		if (sort.substring(1).equals("salary")) {
			return sort.startsWith("-") ? findBySalaryBetweenOrderBySalaryDesc(minSalary, maxSalary, offset, limit)
					: findBySalaryBetweenOrderBySalaryAsc(minSalary, maxSalary, offset, limit);
		}
		return null;

		

	}
	
	public boolean correctColumns(String sort) {
		String newSort = sort.substring(1);
		return newSort.matches("id|name|login|salary") ? true : false;
	}

	public List<Employee> findBySalaryBetweenOrderByIdDesc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByIdDesc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderByIdAsc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByIdAsc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderByNameDesc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByNameDesc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderByNameAsc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByNameAsc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderByLoginDesc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByLoginDesc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderByLoginAsc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderByLoginAsc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
	}

	public List<Employee> findBySalaryBetweenOrderBySalaryDesc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryDesc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));

	}

	public List<Employee> findBySalaryBetweenOrderBySalaryAsc(double minSalary, double maxSalary,
			int offset, int limit) {
		List<Employee> employees = repository.findBySalaryBetweenOrderBySalaryAsc(minSalary, maxSalary);
		return employees.subList(offset, Math.min(employees.size(), limit));
				
	}

	public Optional<Employee> findEmployeeById(int id) {
		return repository.findById(id);
	}

	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> findAllEmployees() {
		return repository.findAll();
	}
	
	public void deleteEmployee(Employee employee) {
		repository.delete(employee);
	}

}
