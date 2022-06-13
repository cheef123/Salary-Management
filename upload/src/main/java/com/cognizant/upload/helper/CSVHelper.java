package com.cognizant.upload.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.upload.entity.Employee;
import com.cognizant.upload.exception.ColumnSizeException;
import com.cognizant.upload.exception.EmptyFileException;
import com.cognizant.upload.exception.NegativeSalaryException;
import com.cognizant.upload.exception.NonUniqueIdException;
import com.cognizant.upload.exception.NonUniqueLoginException;

/**
 * This class is used to check if a {@link MultipartFile} is of csv type
 * @author cheef
 *
 */
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

}
