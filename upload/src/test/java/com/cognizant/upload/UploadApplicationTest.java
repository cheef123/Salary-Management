package com.cognizant.upload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UploadApplicationTest {

//	@Mock
	UploadApplication uploadApplication = new UploadApplication();
	
	@Test
	public void contextLoads() {
		assertNotNull(uploadApplication);;
	}

}
