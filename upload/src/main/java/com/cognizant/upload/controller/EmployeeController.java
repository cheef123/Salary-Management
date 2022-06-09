package com.cognizant.upload.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.helper.CSVHelper;
import com.cognizant.upload.pojo.ResponseMessage;
import com.cognizant.upload.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				service.save(file);
				ResponseMessage message = new ResponseMessage("File uploaded successfully: " + file.getOriginalFilename());
				return new ResponseEntity<ResponseMessage>(message,HttpStatus.OK);
			} catch (Exception ex) {
				log.info(ex.getMessage());
				return new ResponseEntity<String>("Upload failed: " + file.getOriginalFilename(),HttpStatus.EXPECTATION_FAILED);
			}
		} else {
			return new ResponseEntity<String>("File format should be in csv!",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees(){
		try {
			List<Employee> employees = service.getAllEmployees();
			if (employees.isEmpty()) {
				ResponseMessage message = new ResponseMessage("Database is empty!");
				return new ResponseEntity<ResponseMessage>(message,HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
			}
		} catch (Exception ex) {
			return new ResponseEntity<String>("Internal server error!",HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}

}
