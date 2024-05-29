package com.nimesa.assignment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.nimesa.assignment"})
@ComponentScan("com.nimesa.assignment")
@EnableAspectJAutoProxy
@EnableCaching
@Slf4j
public class AssignmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(AssignmentApplication.class, args);
		log.info("APPLICATION IS UP AND RUNNING");
	}

}
