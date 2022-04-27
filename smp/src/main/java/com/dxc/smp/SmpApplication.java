package com.dxc.smp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmpApplication.class, args);
		System.out.println("SMP is running");
	}

}
