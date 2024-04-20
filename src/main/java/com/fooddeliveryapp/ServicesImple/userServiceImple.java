package com.fooddeliveryapp.ServicesImple;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Exceptions.UsernameNotFoundException;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Services.userService;

@Service
public class userServiceImple implements userService{
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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


	@Override
	public User getUserById(int id) {
		User user = uRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
		return user;
	}

	@Override
	public List<User> getAllUser() {
		List<User> allUser = uRepository.findAll();
		return allUser;
	}

	@Override
	public User updateUser(User user, int updateUserId) {		
		
		User updatedUser = uRepository.findById(updateUserId).orElseThrow(()-> new ResourceNotFoundException("User","Id",updateUserId));
		if(user.getRoles()!=null) {
			updatedUser.setRoles(user.getRoles());
		}
		if(user.getPassword()!=null) {
			updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		if(user.getAddress()!=null) {
			updatedUser.setAddress(user.getAddress());
		}
		if(user.getId()!=0) {
			updatedUser.setId(user.getId());
		}
		if(user.getFullName()!=null) {
			updatedUser.setFullName(user.getFullName());
		}
		if(user.getEmail()!=null) {
			updatedUser.setEmail(user.getEmail());
		}
		if(user.getBookmarksRestaurants()!=null) {
			updatedUser.getBookmarksRestaurants();
		}
		if(user.getEmail()!=null) {
			updatedUser.setEmail(user.getEmail());
		}
		User updatedUser2 = uRepository.save(updatedUser);
		return updatedUser2;
	}

	@Override
	public void deleteUser(int id) {
		User user = uRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
		uRepository.delete(user);
	}

}
