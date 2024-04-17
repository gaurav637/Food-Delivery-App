package com.fooddeliveryapp.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Config.JwtProvider;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Payload.authRequest;
import com.fooddeliveryapp.Payload.authResponse;
import com.fooddeliveryapp.Repository.CartRepository;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.authenticationServices;
import com.fooddeliveryapp.Services.userService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class authController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private userService uService;
    
    @Autowired
    private JwtProvider jProvider;
    
    @Autowired
    private authenticationServices aServices;
    
    
    @Autowired
    private CartRepository cRepository;


    @PostMapping("/signUp")
    public ResponseEntity<?> createUserHandler(@RequestBody User user)throws Exception {
    	Optional<User> isUserExist =  uService.findUserByEmail(user.getEmail());
    	System.out.println("User email address >>>>>>>>>>>>>> ------"+user.getEmail());
    	System.out.println("isUserExist value -----------"+isUserExist);
    	//System.out.println("User ")
    	if(isUserExist.isPresent()) {
    		log.info("Email is already used");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Email is already used!",false));
    	}
    	User newUser = aServices.signUpUser(user);
    	return ResponseEntity.status(HttpStatus.CREATED).body(newUser);   
    }
        
     
    @PostMapping("/signIn")
    public ResponseEntity<authResponse> login(@RequestBody authRequest aRequest){
		authResponse response = aServices.signInUser(aRequest);	
    	return new ResponseEntity<authResponse>(response,HttpStatus.OK);	
	}
}