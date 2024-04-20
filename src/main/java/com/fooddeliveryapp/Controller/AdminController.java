package com.fooddeliveryapp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	@GetMapping
	public ResponseEntity<String> hello(){
		String str = "Hi Admin";
		return new ResponseEntity<String>(str,HttpStatus.OK);
	}

}
