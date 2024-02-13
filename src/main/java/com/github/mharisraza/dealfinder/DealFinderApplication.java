package com.github.mharisraza.dealfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.github.mharisraza.dealfinder.*"})
public class DealFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealFinderApplication.class, args);
	}

}
