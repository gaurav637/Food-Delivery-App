package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.User;

public interface userRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String username);
}