package com.cognizant.upload.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConcurrentFlagTest {

	private ConcurrentFlag concurrentFlag;
	
	@BeforeEach
	public void testMethod() {
		concurrentFlag = new ConcurrentFlag(1,true);
	}
	
	@Test
	public void testGetSetConcurrent() {
		concurrentFlag.setConcurrent(false);
		assertEquals(false, concurrentFlag.isConcurrent());
	}

	@Test
	public void testGetConcurrentId() {
		assertEquals(1, concurrentFlag.getId());
	}
}
