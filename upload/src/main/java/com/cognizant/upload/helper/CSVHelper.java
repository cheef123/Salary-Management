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

import javax.persistence.Enumerated;

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
			int rowCount = 1;
			for (CSVRecord csvRecord: csvRecords) {
				String id = csvRecord.get("id");
				String login = csvRecord.get("login");
				String name = csvRecord.get("name");
				String salary = csvRecord.get("salary");
				
				if (id.startsWith("#")) {
					rowCount++;
					continue;
				}
				if (id.isBlank()||login.isBlank()||name.isBlank()||salary.isBlank()) {
					throw new NullPointerException("Missing data in row: " + rowCount);
				}
				if (idMap.containsKey(id)){
					throw new NonUniqueIdException("ID is repeated: " + id);
				}
				
				if (loginMap.containsKey(login)) {
					throw new NonUniqueLoginException("Login is repeated: " + login);

				}
				
				idMap.put(id, 1);
				loginMap.put(login, 1);
				
				Employee employee = new Employee(id, login, name,Double.parseDouble(salary));
				employees.add(employee);
				rowCount++;
			} 
			return employees;	
			} catch (IOException ex) {
				throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
		}
	}

}
