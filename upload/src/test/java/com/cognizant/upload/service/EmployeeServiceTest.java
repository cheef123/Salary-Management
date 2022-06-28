package com.cognizant.upload.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.h2.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
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
import com.cognizant.upload.exception.SalaryFormatException;
import com.cognizant.upload.repository.ConcurrentFlagRepository;
import com.cognizant.upload.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@MockBean
	private ConcurrentFlagRepository flagRepository;

	Employee employee = new Employee("e001", "harryp", "potter", 1000);
	Employee employee1 = new Employee("e002", "p", "potter", 2000);
	List<Employee> employees = new ArrayList<>(Arrays.asList(employee, employee1));

	@Test
	public void hasCSVFormatTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "some xml".getBytes());
		assertEquals(true, employeeService.hasCSVFormat(file));
	}

	@Test
	public void noCSVFormatTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/txt", "some xml".getBytes());
		assertEquals(false, employeeService.hasCSVFormat(file));
	}

	@Test
	public void setConcurrentFlagTest() {
		ConcurrentFlag flag = new ConcurrentFlag(1, false);
		when(flagRepository.save(Mockito.any(ConcurrentFlag.class))).thenReturn(new ConcurrentFlag(1, true));
		assertEquals(true, employeeService.setConcurrentFlag(flag, true).isConcurrent());
	}

	@Test
	public void getAllEmployeesTest() {
		when(employeeRepository.findAll()).thenReturn(employees);
		assertEquals(2, employeeService.getAllEmployees().size());
		assertEquals("e001", employeeService.getAllEmployees().get(0).getEmpId());
		assertEquals(2000, employeeService.getAllEmployees().get(1).getEmpSalary());
	}

//	@Test
//	public void flagIsConcurrentTest()
//			throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException,
//			ColumnSizeException, EmptyFileException, ConcurrentUploadException, SalaryFormatException {
//		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
//				("id,login,name ,salary\r\n" + "e0001,hpotter,Harry Potter,1234.00\r\n"
//						+ "e0002,rwesley,Ron Weasley,19234.5\r\n" + "\r\n" + "").getBytes());
//		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
//		employeeService.save(file);
//		verify(flagRepository, times(5)).save(new ConcurrentFlag(1, false));
//	}

	@Test
	public void columnSizeLessThan4Test() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "1,2,3".getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		Exception exception = assertThrows(ColumnSizeException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Number of columns should be 4 instead of"));
	}

	@Test
	public void columnSizeMoreThan4Test() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "1,2,3,4,5".getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		Exception exception = assertThrows(ColumnSizeException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Number of columns should be 4 instead of"));
	}

	@Test
	public void emptyFileExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "1,2,3,4".getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		Exception exception = assertThrows(EmptyFileException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("File is empty!"));

	}

	@Test
	public void idNullPointerExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + ",hpotter ,Harry Potter,1234.00\r\n"
						+ "e0002,rwesley,Ron Weasley,19234.5\r\n" + "\r\n" + "").getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		Exception exception = assertThrows(NullPointerException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Missing data in row:"));
	}

	@Test
	public void loginNullPointerExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0001, ,Harry Potter,1234.00\r\n"
						+ "e0002,rwesley,Ron Weasley,19234.5\r\n" + "\r\n" + "").getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		Exception exception = assertThrows(NullPointerException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Missing data in row:"));
	}

	@Test
	public void nameNullPointerExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", ("id,login,name ,salary\r\n"
				+ "e0001,hpotter,,1234.00\r\n" + "e0002,rwesley,Ron Weasley,19234.5\r\n" + "\r\n" + "").getBytes());
		Exception exception = assertThrows(NullPointerException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Missing data in row:"));
	}

	@Test
	public void salaryNullPointerExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0001,hpotter,harry potter,\r\n"
						+ "e0002,rwesley,Ron Weasley,19234.5\r\n" + "\r\n" + "").getBytes());
		Exception exception = assertThrows(NullPointerException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Missing data in row:"));
	}

	@Test
	public void skipRowsWithCommentTest() throws NonUniqueIdException, NonUniqueLoginException, NegativeSalaryException,
			ColumnSizeException, EmptyFileException, SalaryFormatException, IOException {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "#this is a comment\r\n" + "e0004,voldey,voldemort,4235.9").getBytes());
		List<Employee> employees = employeeService.csvToEmployees(file.getInputStream());
		assertEquals(1, employees.size());
		assertEquals("voldey", employees.get(0).getEmpLogin());
		assertEquals(4235.9, employees.get(0).getEmpSalary());
	}

	@Test
	public void salaryFormatExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", ("id,login,name ,salary\r\n"
				+ "e0001,hpotter,Harry Potter,$.$\r\n" + "e0002,rwesley,Ron Weasley,hello\r\n" + "").getBytes());
		Exception exception = assertThrows(SalaryFormatException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});

		assertTrue(exception.getMessage().contains("Wrong salary format in row:"));
	}

	@Test
	public void negativeSalaryExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0001,hpotter,Harry Potter,1234.00\r\n"
						+ "e0002,rwesley,Ron Weasley,19234.5\r\n" + "e0003,ssnape,snape,-23.32").getBytes());
		Exception exception = assertThrows(NegativeSalaryException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});

		assertTrue(exception.getMessage().contains("Salary must be positive! Error found in row:"));
	}

	@Test
	public void nonUniqueLoginExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", ("id,login,name ,salary\r\n"
				+ "e0001,hpotter_repeat,Harry Potter,1234.00\r\n" + "e0007,hpotter_repeat,snape,1232.00").getBytes());
		Exception exception = assertThrows(NonUniqueLoginException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("Login is repeated:"));
	}

	@Test
	public void nonUniqueIdExceptionTest() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", ("id,login,name ,salary\r\n"
				+ "e0001,hpotter_repeat,Harry Potter,1234.00\r\n" + "e0001,hpotter,snape,1232.00").getBytes());
		Exception exception = assertThrows(NonUniqueIdException.class, () -> {
			employeeService.csvToEmployees(file.getInputStream());
		});
		assertTrue(exception.getMessage().contains("ID is repeated:"));
	}

	@Test
	public void loginConflictException() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0004,voldey,voldemort,4235.9").getBytes());
		Employee employee1 = new Employee("e0005", "voldey", "voldemort", 4235.9);
		List<Employee> employees1 = new ArrayList<>();
		employees1.add(employee1);
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		when(employeeRepository.findByEmpLogin(Mockito.any(String.class))).thenReturn(employees1);
		Exception exception = assertThrows(LoginConflictException.class, () -> {
			employeeService.save(file);
		});
		assertTrue(exception.getMessage().contains("Conflict with existing login: "));

	}

	@Test
	public void saveSuccessful()
			throws NonUniqueIdException, NonUniqueLoginException, LoginConflictException, NegativeSalaryException,
			ColumnSizeException, EmptyFileException, ConcurrentUploadException, SalaryFormatException, IOException {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0004,dude,dudleyyy,4235.9").getBytes());
		Employee employee1 = new Employee("e0004", "dude", "dudleyyy", 4235.9);
		List<Employee> employees1 = new ArrayList<>();
		employees1.add(employee1);
		List<Employee> emptyEmployees = new ArrayList<>();
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, false)));
		when(employeeRepository.findByEmpLogin(Mockito.any(String.class))).thenReturn(emptyEmployees);
		when(employeeRepository.saveAll(employees1)).thenReturn(employees1);
		employeeService.save(file);
		verify(employeeRepository, times(1)).saveAll(employees1);

	}
	
	@Test
	public void concurrentUploadException() {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv",
				("id,login,name ,salary\r\n" + "e0004,dude,dudleyyy,4235.9").getBytes());
		when(flagRepository.findById(1)).thenReturn(Optional.of(new ConcurrentFlag(1, true)));
		Exception exception = assertThrows(ConcurrentUploadException.class, ()->{
			employeeService.save(file);
		});
		assertTrue(exception.getMessage().contains("Concurrent upload detected! Please try again later!"));

	}

}
