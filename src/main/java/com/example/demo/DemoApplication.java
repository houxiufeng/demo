package com.example.demo;

import com.example.demo.common.SpringContextUti;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(DemoApplication.class, args);
		SpringContextUti.setApplicationContext(app);
	}

}
