package com.fedag.fedagmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FedagmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FedagmusicApplication.class, args);
	}

}
