package com.fooddeliveryapp.Services;

import com.fooddeliveryapp.Model.User;

public interface userService {
	
	public User findUserByJwtToken(String token)throws Exception;
	
	public User findUserByEmail(String email)throws Exception;
	
	public User findUserById(int id);

}
