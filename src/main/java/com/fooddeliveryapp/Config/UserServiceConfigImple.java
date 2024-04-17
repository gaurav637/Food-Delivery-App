package com.fooddeliveryapp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.Exceptions.UsernameNotFoundException;
import com.fooddeliveryapp.Repository.userRepository;
import lombok.RequiredArgsConstructor;

@Service
public class UserServiceConfigImple implements UserServiceConfig{
	
	@Autowired
	private  userRepository uRepository;
	
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) {
				return uRepository.findByEmail(username)
						.orElseThrow(()-> new UsernameNotFoundException("User Not Found !",username));
			}
		};
	}
	
}
