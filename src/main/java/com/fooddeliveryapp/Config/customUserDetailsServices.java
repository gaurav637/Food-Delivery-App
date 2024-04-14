package com.fooddeliveryapp.Config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fooddeliveryapp.Model.USER_ROLE;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;


@Service
public class customUserDetailsServices implements UserDetailsService{

	@Autowired
	private userRepository uRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uRepo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found with username : "+username);
		}
		
		USER_ROLE role = user.getRoles();
		if(role==null) {
			role = USER_ROLE.ROLE_USER;
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
	
	
	
	

}
