package com;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@SpringBootApplication
public class RailWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailWayApplication.class, args);
		
	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

           User admin = new User(1, "admin", passwordEncode.encode("password"), roles);

			userRepository.save(admin);
		};
	}
}
