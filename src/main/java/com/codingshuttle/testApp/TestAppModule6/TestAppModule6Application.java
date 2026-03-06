package com.codingshuttle.testApp.TestAppModule6;

import com.codingshuttle.testApp.TestAppModule6.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RequiredArgsConstructor
public class TestAppModule6Application implements CommandLineRunner {

//	private final DataService dataService;

	@Value("${my.variable}")
	private String myVariable;

	public static void main(String[] args) {
		SpringApplication.run(TestAppModule6Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("My Variable is : "+myVariable);
//		System.out.println("The Data is From : "+dataService.getData());
	}
}
