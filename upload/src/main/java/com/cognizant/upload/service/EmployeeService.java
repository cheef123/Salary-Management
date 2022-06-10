package com.cognizant.upload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.LoginConflictException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;
import com.cognizant.upload.helper.CSVHelper;
import com.cognizant.upload.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void save(MultipartFile file) throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException, ColumnSizeException, EmptyFileException {
		try {
			List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
			for (Employee employee:employees) {
				 List<Employee> findByEmpLogin = employeeRepository.findByEmpLogin(employee.getEmpLogin());
				 // throw exception if login exist but different id
				if (!findByEmpLogin.isEmpty() && !findByEmpLogin.get(0).getEmpId().equals(employee.getEmpId())) {
					throw new LoginConflictException("Conflict with existing login: " + employee.getEmpLogin());
				}
			}
			employeeRepository.saveAll(employees);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to store CSV data: " + ex.getMessage());
		}
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
}
