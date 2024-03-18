package com.example.demo;

import java.io.Console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mysql.cj.log.Log;

@SpringBootApplication
public class EncheresEniApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncheresEniApplication.class, args);
		System.out.println("test");
	}

}
