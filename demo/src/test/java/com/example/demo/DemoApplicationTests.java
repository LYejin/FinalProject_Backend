package com.example.demo;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private EmployeeService employeeService;

	@Test
	void contextLoads() {

		System.out.println("sdiosniodionvonisdvonisdoinvino");

	}

}
