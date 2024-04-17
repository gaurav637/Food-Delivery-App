package com.fooddeliveryapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddeliveryapp.Model.USER_ROLE;
import com.fooddeliveryapp.Model.User;

public interface userRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String username);
	
	User findByRoles(USER_ROLE roles);
	
	
}