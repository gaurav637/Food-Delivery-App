package com.fooddeliveryapp.Controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Config.customUserDetailsServices;
import com.fooddeliveryapp.Config.Request.authRequest;
import com.fooddeliveryapp.Config.Response.authResponse;
import com.fooddeliveryapp.Model.Cart;
import com.fooddeliveryapp.Model.USER_ROLE;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.CartRepository;
import com.fooddeliveryapp.Repository.userRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class authController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private userRepository uRepository;
    
    @Autowired
    private JwtProvider jProvider;
    
    @Autowired
    private customUserDetailsServices cuds;
    
    @Autowired
    private CartRepository cRepository;


    @PostMapping("/signUp")
    public ResponseEntity<authResponse> createUserHandler(@Valid @RequestBody User user)throws Exception {
    	User isUserExist =  uRepository.findByEmail(user.getEmail());
    	if(isUserExist!=null) {
    		log.info("Email is already used");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new authResponse(null,"Email is already used!",null));
    	}
    	
    	User createdUser = new User();
    	createdUser.setEmail(user.getEmail());
    	createdUser.setFullName(user.getFullName());
    	createdUser.setRoles(user.getRoles());
    	createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
    	User user2 = uRepository.save(createdUser);
    	Cart cart = new Cart();
    	cart.setUser(user2);
    	cRepository.save(cart);
    	Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	String token = jProvider.generateToken(authentication);
    	
    	authResponse aResponse = new authResponse();
    	aResponse.setJwtToken(token);
    	aResponse.setMessage("Register Success.");   
    	aResponse.setRole(user2.getRoles());
    	return new ResponseEntity<>(aResponse,HttpStatus.CREATED);
    }
    

    @PostMapping("/signIn")
    public ResponseEntity<authResponse> login(@RequestBody authRequest aRequest){
    	
		String username = aRequest.getUsername();
		String password = aRequest.getPassword();
		String token = null;
		String roles = null;
		
		try {
			Authentication authentication = authenticate(username,password);
	        token = jProvider.generateToken(authentication);
	        
	        Collection<? extends GrantedAuthority>authorities = authentication.getAuthorities();
	        roles = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
			
		}catch(Exception e) {
			if(e.getMessage()=="Invalid Username") {
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new authResponse(null,"username Invalid!",null));

			}
			if(e.getMessage()=="Invalid Password")	{		
	    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new authResponse(null,"password Invalid!",null));

			}
			e.printStackTrace();
		}
		
    	authResponse aResponse = new authResponse();
    	aResponse.setJwtToken(token);
    	aResponse.setMessage("Register Success.");   
    	aResponse.setRole(USER_ROLE.valueOf(roles));
    	return new ResponseEntity<authResponse>(aResponse,HttpStatus.CREATED);	
	}

	private Authentication authenticate(String username, String password)throws Exception {
		UserDetails userDetails = cuds.loadUserByUsername(username);
		if(userDetails==null) {
			
			throw new Exception("Invalid Username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new Exception("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}