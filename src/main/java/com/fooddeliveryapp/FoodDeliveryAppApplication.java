package com.fooddeliveryapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fooddeliveryapp.Model.USER_ROLE;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;

@SpringBootApplication
public class FoodDeliveryAppApplication implements CommandLineRunner{
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryAppApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = uRepository.findByRoles(USER_ROLE.ADMIN);
		if(adminAccount==null) {
			User user = new User();
			user.setEmail("negiAdmin2004@gmail.com");
			user.setPassword(passwordEncoder.encode("negiAdmin2004"));
			user.setFullName("Rahul Negi");
			user.setRoles(USER_ROLE.ADMIN);
			uRepository.save(user);
			
		}
		
	}

}
