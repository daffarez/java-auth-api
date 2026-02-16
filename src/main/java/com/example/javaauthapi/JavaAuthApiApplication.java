package com.example.javaauthapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavaAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaAuthApiApplication.class, args);
	}

}
