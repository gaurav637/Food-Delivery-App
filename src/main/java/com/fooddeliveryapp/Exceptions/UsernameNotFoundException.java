package com.fooddeliveryapp.Exceptions;

public class UsernameNotFoundException  extends RuntimeException{
	
	private String message;
	
	private String username;
	
	public UsernameNotFoundException() {
		
	}
	
	public UsernameNotFoundException(String message,String username) {
		super(String.format("User not found with user name: %s ",username));
		this.message = message;
		this.username = username;
	}

}
