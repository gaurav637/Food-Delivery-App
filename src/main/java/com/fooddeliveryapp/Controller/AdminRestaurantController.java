package com.fooddeliveryapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddeliveryapp.DTO.CreateRestaurantRequest;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/admin")
public class AdminRestaurantController {
	
	@Autowired
	private restaurantServices rServices;
	
	@Autowired
	private userService uService;
	
	@Autowired
	private userRepository uRepository;
	
	@PostMapping("create")
	public ResponseEntity<?> createRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
				
		try {	
			String jwt1 = jwt.split(" ")[1].trim();
			
			User user = uService.findUserByJwtToken(jwt1);			
			Restaurant restaurant = rServices.createRestaurant(req, user);
			return new ResponseEntity<Restaurant>(restaurant,HttpStatus.CREATED);
			
		}catch(Exception e) {
			String errorMessage = "Failed to create Restaurant:: !"+e.getMessage();
			return new ResponseEntity<>(errorMessage ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update-restaurant/{restId}")
	public ResponseEntity<?> updateRestaurants(@RequestBody CreateRestaurantRequest req,@RequestHeader("Authorization") String token, @PathVariable("restId") int restId){
		try {
			Restaurant restaurant = rServices.updateRestaurant(restId, req);
			User user = uService.findUserByJwtToken(token);
			return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to update Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
	
	@DeleteMapping("delete-restaurant/{restId}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable("restId") int restId,@RequestHeader("Authorization") String token){
		try {
			
			String jwt = token.split(" ")[1].trim();
			this.rServices.deleteRestaurant(restId);
			User user = uService.findUserByJwtToken(jwt);
			ApiResponse response = new ApiResponse("Restaurant is deleted successfully.",true);
			return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Restaurant is deleted successfully"+e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/update-restaurant-status/{restId}")
	public ResponseEntity<?> updateRestaurantStatusInController(@PathVariable("restId") int id,@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim(); 
			Restaurant rest = rServices.updateRestaurantStatus(id);
			User user = uService.findUserByJwtToken(jwt);
			return new ResponseEntity<>(rest,HttpStatus.OK);
			
		}catch(Exception e) {
			String errorMessage = "Failed to update Restaurant status!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
	
	@GetMapping("/get-restaurant-by-user-id/{userId}")
	public ResponseEntity<?> getRestaurantByUserId(@PathVariable("userId") int id,@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim(); 
			User user = uService.findUserByJwtToken(jwt);
			Restaurant restaurant = rServices.getRestaurantByUserId(user.getId());
			return new ResponseEntity<>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String  errorMessage = "Restaurant is not present with userId:"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
