package com.fooddeliveryapp.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fooddeliveryapp.Model.User;


public interface userService {
	
	public User findUserByJwtToken(String token)throws Exception;	
	
	@Query(value = "SELECT * FROM user WHERE email = :email1")
	Optional<User> findUserByEmail(@Param("email1") String email1);
	
	public User getUserById(int id);
	
	public List<User> getAllUser();
	
	public User updateUser(User user,int updateUserId);
	
	public void deleteUser(int id); 

	
}
