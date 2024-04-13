package com.sosoburger.toaster;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ToasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToasterApplication.class, args);
	}

}
