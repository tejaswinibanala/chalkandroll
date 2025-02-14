package com.school.chalkandroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.school.chalkandroll.repository")
@EntityScan("com.school.chalkandroll.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ChalkAndRollApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChalkAndRollApplication.class, args);
	}

}
