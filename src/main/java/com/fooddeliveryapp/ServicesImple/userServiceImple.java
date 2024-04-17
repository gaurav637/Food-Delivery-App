package com.fooddeliveryapp.ServicesImple;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Exceptions.UsernameNotFoundException;
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
		String email = jprovider.getUsernameFromToken(token);
		User user = uRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User",email));
		return user;
	}

	@Override
	public Optional<User> findUserByEmail(String email){
		Optional<User> user = uRepository.findByEmail(email);
		return user;
	}
//
//	@Override
//	public User findUserById(int id) {
//		User user = uRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",id));
//		return user;
//	}

}
