package com.provatecnica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.provatecnica"})
public class ProvaTecnicaApp {

	public static void main(String[] args) {
		SpringApplication.run(ProvaTecnicaApp.class, args);
	}
}
