package com.fooddeliveryapp.ServicesImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Services.userService;

@Service
public class userServiceImple implements userService{
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private JwtProvider jprovider;

	@Override
	public User findUserByJwtToken(String token) {
		String email = jprovider.getEmailFromToken(token);
		User user = uRepository.findByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email)throws Exception {
		User user = uRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("user not found !!");
		}
		return user;
	}

}
