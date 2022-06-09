package com.cognizant.upload.helper;

import java.io.BufferedReader;
import java.io.IOError;
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
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;

public class CSVHelper {
	public static String TYPE = "text/csv";

	/**
	 * Check if filetype is of "text/csv"
	 * 
	 * @param file
	 * @return
	 */
	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Employee> csvToEmployees(InputStream is) throws NonUniqueIdException, NonUniqueLoginException{
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			List<Employee> employees = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			Map<String, Integer> idMap = new HashMap<>();
			Map<String, Integer> loginMap = new HashMap<>();
			for (CSVRecord csvRecord: csvRecords) {
				if (csvRecord.get("id").startsWith("#")) {
					continue;
				}
				if (idMap.containsKey(csvRecord.get("id"))){
					throw new NonUniqueIdException("ID is repeated: " + csvRecord.get("id"));
				}
				
				if (loginMap.containsKey(csvRecord.get("login"))) {
					throw new NonUniqueLoginException("Login is repeated: " + csvRecord.get("login"));

				}
				idMap.put(csvRecord.get("id"), 1);
				loginMap.put(csvRecord.get("login"), 1);
				
				Employee employee = new Employee(
						csvRecord.get("id"), 
						csvRecord.get("login"), 
						csvRecord.get("name"),
						Double.parseDouble(csvRecord.get("salary")));
				employees.add(employee);
			} 
			return employees;	
			} catch (IOException ex) {
				throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
		}
	}

}
