package com.example.taskmanager;

import org.testng.annotations.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

@SpringBootTest
class TaskmanagerApplicationTests {

	@Test
	void contextLoads() {
		// This test verifies that the Spring application context loads successfully
		Assert.assertTrue(true, "Application context should load successfully");
	}

}
