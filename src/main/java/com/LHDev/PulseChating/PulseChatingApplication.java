package com.LHDev.PulseChating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.LHDev.PulseChating.models")
@EnableJpaRepositories(basePackages = "com.LHDev.PulseChating.repositories")
public class PulseChatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PulseChatingApplication.class, args);
	}

}
