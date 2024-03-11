package com.backend.lunchapp.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.lunchapp.dao.UserRepository;
import com.backend.lunchapp.model.User;

@Configuration
public class UserConfiguration {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
	@Bean
	CommandLineRunner commandLineRunner(UserRepository repo) {
		return args -> {
			User userone = new User(
					"james@tech.gov.sg",
					"James"
					);
			User usertwo = new User(
					"bob@tech.gov.sg",
					"Bob"
					);
			User userthree = new User(
					"jane@tech.gov.sg",
					"Jane"
					);
			repo.saveAll(List.of(userone,usertwo,userthree));

		};	
	}

}
