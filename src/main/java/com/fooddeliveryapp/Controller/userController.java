package com.fooddeliveryapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("/api/users")
public class userController {

	@Autowired
	private userService uService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String token)throws Exception{
		User user = this.uService.findUserByJwtToken(token);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	public ResponseEntity<User> findUserByEmail(@RequestBody String email)throws Exception{
		User user = this.uService.findUserByEmail(email);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
}
