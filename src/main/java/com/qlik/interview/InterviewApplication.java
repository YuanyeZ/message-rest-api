package com.qlik.interview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterviewApplication {
	private static final Logger log = LoggerFactory.getLogger(InterviewApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}
}
