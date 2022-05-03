package com.example.demo;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserService userService;

	@Test
	public void testListUsers() {
		userService.listByCondition(null).forEach(System.out::println);
	}

}
