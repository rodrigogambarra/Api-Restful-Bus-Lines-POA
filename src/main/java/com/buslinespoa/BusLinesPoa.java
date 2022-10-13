package com.buslinespoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.buslinespoa"})
public class BusLinesPoa {

	public static void main(String[] args) {
		SpringApplication.run(BusLinesPoa.class, args);
	}
}
