package com.sosoburger.toaster;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ToasterApplication {

	public static void main(String[] args) {
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(FireBase.getCredentials()).build();
		FirebaseApp.initializeApp(options);
		SpringApplication.run(ToasterApplication.class, args);
	}

}
