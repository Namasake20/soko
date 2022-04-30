package com.caleb.Tiba;

import com.caleb.Tiba.model.AppUser;
import com.caleb.Tiba.model.Role;
import com.caleb.Tiba.service.DataAccessService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class TibaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TibaApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(DataAccessService dataAccessService){
		return args -> {
			dataAccessService.saveRole(new Role(null,"ROLE_USER"));
			dataAccessService.saveRole(new Role(null,"ROLE_ADMIN"));
			dataAccessService.saveRole(new Role(null,"ROLE_MANAGER"));

			dataAccessService.saveUser(new AppUser(null,"caleb","masake@gmail.com","1234",new ArrayList<>()));
			dataAccessService.saveUser(new AppUser(null,"alice","alice@gmail.com","1234",new ArrayList<>()));
			dataAccessService.saveUser(new AppUser(null,"bob","bob@gmail.com","1234",new ArrayList<>()));

			dataAccessService.addRoleToUser("masake@gmail.com","ROLE_USER");
			dataAccessService.addRoleToUser("alice@gmail.com","ROLE_USER");
			dataAccessService.addRoleToUser("alice@gmail.com","ROLE_ADMIN");
		};

	}

}
