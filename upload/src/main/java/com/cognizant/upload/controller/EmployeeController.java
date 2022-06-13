package com.cognizant.upload.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.ConcurrentUploadException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.LoginConflictException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;
import com.cognizant.upload.exception.SalaryFormatException;
import com.cognizant.upload.helper.CSVHelper;
import com.cognizant.upload.pojo.ResponseMessage;
import com.cognizant.upload.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is having all the endpoints related to uploading and retrieving purposes. 
 * 
 * @author cheef
 *
 */

@CrossOrigin("http://localhost:4200")
@RestController
@Slf4j
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	/**
	 * This method first checks if the uploaded file is of a csv format. If it is, it will call upon {@link EmployeeService} to save the file into the repository.
	 * If not, it will return a {@link ResponseEntity} with bad request.
	 * @param file
	 * @return
	 * @throws NonUniqueIdException If current id exists in database
	 * @throws NonUniqueLoginException If current login exists in database
	 * @throws LoginConflictException If login exist in database with a different id
	 * @throws NegativeSalaryException If salary is negative
	 * @throws ColumnSizeException If column size is less than or more than 4
	 * @throws EmptyFileException If csv file is empty
	 * @throws ConcurrentUploadException If an upload is still ongoing 
	 * @throws SalaryFormatException If format of salary is nondecimal
	 */
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException, ColumnSizeException, EmptyFileException, ConcurrentUploadException, SalaryFormatException{
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				service.save(file);
				ResponseMessage message = new ResponseMessage("File uploaded successfully: " + file.getOriginalFilename());
				return new ResponseEntity<ResponseMessage>(message,HttpStatus.OK);
			} catch (ConcurrentUploadException ex) {
				throw new ConcurrentUploadException(ex.getMessage());
			} catch (ColumnSizeException ex) {
				throw new ColumnSizeException(ex.getMessage());
			} catch (EmptyFileException ex) {
				throw new EmptyFileException(ex.getMessage());
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException(ex.getMessage());
			} catch (NonUniqueIdException ex) {
				throw new NonUniqueIdException(ex.getMessage());
			} catch (NonUniqueLoginException ex) {
				throw new NonUniqueLoginException(ex.getMessage());
			} catch (NullPointerException ex) {
				throw new NullPointerException(ex.getMessage());
			} catch (LoginConflictException ex) {
				throw new LoginConflictException(ex.getMessage());
			} catch (SalaryFormatException ex) {
				throw new SalaryFormatException(ex.getMessage());
			} catch (NegativeSalaryException ex) {
				throw new NegativeSalaryException(ex.getMessage());
			} catch (Exception ex) {
				return new ResponseEntity<String>("Upload failed: " + file.getOriginalFilename(),HttpStatus.EXPECTATION_FAILED);
			}
		} else {
			return new ResponseEntity<String>("File format should be in csv!",HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This method retrieves all the {@link Employee} from the database. If database is empty, return forbidden status.
	 * 
	 * @return
	 */
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees(){
		try {
			List<Employee> employees = service.getAllEmployees();
			if (employees.isEmpty()) {
				ResponseMessage message = new ResponseMessage("Database is empty!");
				return new ResponseEntity<ResponseMessage>(message,HttpStatus.FORBIDDEN);
			} else {
				return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<String>("Internal server error!",HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

}
