package net.wickedcorp.usciscasenotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@Slf4j
@EnableJpaRepositories
public class UscisCaseNotifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(UscisCaseNotifierApplication.class, args);
	}

}
