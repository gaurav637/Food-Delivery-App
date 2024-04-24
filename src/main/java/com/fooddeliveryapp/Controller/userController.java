package com.fooddeliveryapp.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("/api/v1/user")
public class userController {

	@Autowired
	private userService uService;
	
	
	
	@GetMapping("get-all-users")
	public ResponseEntity<?> getAllUserInController(@RequestHeader("Authorization") String token) throws Exception{
		
		  try {
			  String jwt = token.split(" ")[1].trim();
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		
		try {
			
			List<User> getAllUser = uService.getAllUser();
			return new ResponseEntity<>(getAllUser,HttpStatus.OK);
			
		}catch(Exception e) {
			String errorMessage = "Failed to Get All Users:: !"+e.getMessage();
			return new ResponseEntity<>(errorMessage ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("get-user-by-id/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable("userId") int id,@RequestHeader("Authorization") String token)throws Exception{
		
		try {
			String jwt = token.split(" ")[1].trim();
		}catch (Exception e) {
			e.printStackTrace();
		}
		User user = uService.getUserById(id);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	
	@PutMapping("update-user")
	public ResponseEntity<?> updateUserDetails(@RequestBody User user,@RequestHeader("Authorization") String jwt) throws Exception{
		    try {
				String token = jwt.split(" ")[1].trim();
				User user1 = uService.findUserByJwtToken(token);
				User updatedUser = uService.updateUser(user, user1.getId());
			    return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	        }catch(Exception e) {
			    String errorMessage = "Failed to update User ! "+e.getMessage();
			    return new ResponseEntity<>(errorMessage,HttpStatus.NOT_MODIFIED);
		     }
	}
	
	@DeleteMapping("user-delete")
	public ResponseEntity<ApiResponse> deleteUserEntity(@RequestHeader("Authorization") String token) throws Exception{
		String jwt = token.split(" ")[1].trim();
		User user = uService.findUserByJwtToken(jwt);
		User user1 = uService.getUserById(user.getId());
		uService.deleteUser(user.getId());
		ApiResponse response = new ApiResponse(String.format("User deleted : username ; %s ",user.getFullName()),true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
	@GetMapping("get-user-by-email")
	public ResponseEntity<User> getUserByEmailId(@RequestParam String email,
			@RequestHeader("Authorization") String token){

		String jwt = token.split(" ")[1].trim();
		Optional<User> user = uService.findUserByEmail(email);
		if (user.isPresent()) {
	        return new ResponseEntity<>(user.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@GetMapping("get-user-by-token")
	public ResponseEntity<?> getUserByToken(@RequestHeader("Authorization") String jwt)throws Exception{
		try {
			String token = jwt.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
			return new ResponseEntity<>(user,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to get user By token !"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		}
	}
}
