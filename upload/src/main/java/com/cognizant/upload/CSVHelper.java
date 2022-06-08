package com.cognizant.upload;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;

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

	public static List<Employee> csvToEmployees(InputStream is){
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			List<Employee> employees = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord: csvRecords) {
				Employee employee = new Employee(
						Integer.parseInt(csvRecord.get("id")), 
						csvRecord.get("login"), 
						csvRecord.get("name"),
						Double.parseDouble("salary"));
				employees.add(employee);
			} 
			return employees;	
			} catch (IOException ex) {
				throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
		}
	}

}
