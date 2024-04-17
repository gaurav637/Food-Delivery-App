package com.fooddeliveryapp.ServicesImple;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Exceptions.UsernameNotFoundException;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Payload.authRequest;
import com.fooddeliveryapp.Payload.authResponse;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Services.authenticationServices;


@Service
public class authenticationServiceImple implements authenticationServices {
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jProvider;
	
	
	public User signUpUser(User user) {
		User createdUser = new User();
    	createdUser.setEmail(user.getEmail());
    	createdUser.setFullName(user.getFullName());
    	createdUser.setRoles(user.getRoles());
    	createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
    	User user2 = uRepository.save(createdUser);
        return user2;
	}


	@Override
	public authResponse signInUser(authRequest auth) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
		var user = uRepository.findByEmail(auth.getUsername()).orElseThrow(()-> new UsernameNotFoundException("user not found with email",auth.getUsername()));
		var jwt = jProvider.doGenerateToken(user);
		var refreshToken = jProvider.doGenerateRefreshToken(new HashMap<>(),user);
		authResponse aResponse = new authResponse();
		aResponse.setJwtToken(jwt);
		aResponse.setRefreshToken(refreshToken);
		String user2 = jProvider.getUsernameFromToken(jwt);
		aResponse.setUsername(user2);
		return aResponse;
				
	}
}
