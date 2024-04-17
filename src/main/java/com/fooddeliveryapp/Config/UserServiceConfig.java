package com.fooddeliveryapp.Config;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceConfig {
	
	UserDetailsService userDetailsService();
}
