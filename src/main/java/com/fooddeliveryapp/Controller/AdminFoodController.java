package com.fooddeliveryapp.Controller;

import org.antlr.v4.runtime.misc.TestRig;
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
import com.fooddeliveryapp.DTO.CreateFoodRequest;
import com.fooddeliveryapp.Model.Foods;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.FoodServices;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminFoodController {
	
	@Autowired
	private FoodServices fServices;
	
	@Autowired
	private userService uService;
	
	@Autowired
	private restaurantServices rest;
	
	@PostMapping("create-food")
	public ResponseEntity<?> createFood(
			@RequestBody CreateFoodRequest req,
			@RequestHeader("Authorization") String token) throws Exception{
		
		 try {
			 String jwt = token.split(" ")[1].trim();
		        User user = uService.findUserByJwtToken(jwt);
		        Restaurant restaurant = rest.getRestaurantById(req.getRestId());
		        Foods foods = fServices.createFood(req, req.getFoodCategory(),restaurant);
				return 	new ResponseEntity<>(foods,HttpStatus.OK);
		 }catch(Exception e) {
			 String errorMessage = "Failed to create new Food"+e.getMessage();
			 return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	@DeleteMapping("delete-food/{foodId}")
	public ResponseEntity<?> deleteFood(
			@PathVariable("foodId") int foodId,
			@RequestHeader("Authorization") String token) throws Exception{
			    String jwt = token.split(" ")[1].trim();
		        User user = uService.findUserByJwtToken(jwt);
		        ApiResponse response = fServices.deleteFood(foodId);
				return 	new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("update-food-status/{foodId}")
	public ResponseEntity<?> updateFoodAvailabilityStatus(
			@PathVariable("foodId") int foodId,
			@RequestHeader("Authorization") String token) throws Exception{
			    try {
			    	String jwt = token.split(" ")[1].trim();
			        User user = uService.findUserByJwtToken(jwt);
			        Foods foods = fServices.updateAvailabilityStatus(foodId);
					return 	new ResponseEntity<>(foods,HttpStatus.OK);
			    }catch(Exception e) {
			    	String errorMessage = "Failed to Update Food Available status!"+e.getMessage();
			    	return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			    }
	}

}
