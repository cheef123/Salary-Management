package com.cognizant.upload.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.ConcurrentFlag;
import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.ConcurrentUploadException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.LoginConflictException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;
import com.cognizant.upload.helper.CSVHelper;
import com.cognizant.upload.repository.ConcurrentFlagRepository;
import com.cognizant.upload.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ConcurrentFlagRepository flagRepository;
	
	public void save(MultipartFile file) throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException, ColumnSizeException, EmptyFileException, ConcurrentUploadException {
		try {
			if (!flagRepository.findById(1).get().isConcurrent()) {
				ConcurrentFlag flag = new ConcurrentFlag(1,true);
				flagRepository.save(flag);
				List<Employee> employees = csvToEmployees(file.getInputStream());
				for (Employee employee:employees) {
					 List<Employee> findByEmpLogin = employeeRepository.findByEmpLogin(employee.getEmpLogin());
					 // throw exception if login exist but different id
					if (!findByEmpLogin.isEmpty() && !findByEmpLogin.get(0).getEmpId().equals(employee.getEmpId())) {
						flag.setConcurrent(false);
						flagRepository.save(flag);
						throw new LoginConflictException("Conflict with existing login: " + employee.getEmpLogin());
					}
					employeeRepository.saveAll(employees);
					flag.setConcurrent(false);
					flagRepository.save(flag);
				}
			} else {
				throw new ConcurrentUploadException("Concurrent upload detected! Please try again later!");
			}
			
		} catch (IOException ex) {
			throw new RuntimeException("Failed to store CSV data: " + ex.getMessage());
		}
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	public List<Employee> csvToEmployees(InputStream is) throws NonUniqueIdException, NonUniqueLoginException, NegativeSalaryException, ColumnSizeException, EmptyFileException{
		try {
			ConcurrentFlag flag = new ConcurrentFlag(1,false);
			flagRepository.save(flag);
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			if (csvParser.getHeaderMap().size() != 4) {
				flagRepository.save(flag);
				throw new ColumnSizeException("Number of columns should be 4 instead of " + csvParser.getHeaderMap().size());
			}
			List<Employee> employees = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			
			if (!csvRecords.iterator().hasNext()) {
				flagRepository.save(flag);
				throw new EmptyFileException("File is empty!");
			}
			
			Map<String, Integer> idMap = new HashMap<>();
			Map<String, Integer> loginMap = new HashMap<>();
			int rowCount = 1;
			for (CSVRecord csvRecord: csvRecords) {
				String id = csvRecord.get("id");
				
				if (id.startsWith("#")) {
					rowCount++;
					continue;
				}
				
				String login = csvRecord.get("login");
				String name = csvRecord.get("name");
				String salary = csvRecord.get("salary");
				
				
				if (id.isBlank()||login.isBlank()||name.isBlank()||salary.isBlank()) {
					flagRepository.save(flag);
					throw new NullPointerException("Missing data in row: " + rowCount);
				}
				
				if (salary.matches("^-\\d+\\.\\d+")) {
					flagRepository.save(flag);
					throw new NegativeSalaryException("Salary must be positive! Error found in row: " + rowCount); 
				}
				if (idMap.containsKey(id)){
					flagRepository.save(flag);
					throw new NonUniqueIdException("ID is repeated: " + id);
				}
				
				if (loginMap.containsKey(login)) {
					flagRepository.save(flag);
					throw new NonUniqueLoginException("Login is repeated: " + login);

				}
				
				idMap.put(id, 1);
				loginMap.put(login, 1);
				
				Employee employee = new Employee(id, login, name,Double.parseDouble(salary));
				employees.add(employee);
				rowCount++;
			} 
			flagRepository.save(flag);
			return employees;	
			} catch (IOException ex) {
				ConcurrentFlag flag = new ConcurrentFlag(1,false);
				flagRepository.save(flag);
				throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
		}
	}	
}
