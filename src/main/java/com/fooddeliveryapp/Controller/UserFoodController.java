package com.fooddeliveryapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Model.Foods;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Services.FoodServices;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api/v1/auth")
public class UserFoodController {
	
	@Autowired
	private FoodServices fServices;
	
	@Autowired
	private restaurantServices rServices;
	
	@Autowired
	private userService uService;
	
	@GetMapping("search-foods")
	public ResponseEntity<?> searchFood(
			@RequestParam String keyword,
			@RequestHeader("Authorization") String token)throws Exception{
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			List<Foods> foods = fServices.searchFood(keyword);
			return new ResponseEntity<>(foods,HttpStatus.OK);
		}catch (Exception e) {
			String errorMessage = "Not Found Any Result"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("get-restaurant-foods/{restId}")
	public ResponseEntity<?> getFoods(
			@RequestParam boolean isVagetarian,
			@RequestParam boolean isNonVage,
			@RequestParam boolean isSeasonal,
			@RequestParam(required = false) String category,
			@PathVariable("restId") int restid,
			@RequestHeader("Authorization") String token)throws Exception{
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
			List<Foods> foods = fServices.getRestaurantFoods(restid, isVagetarian, isNonVage, isSeasonal, category);		
			return new ResponseEntity<>(foods,HttpStatus.OK);
		}catch (Exception e) {
			String errorMessage = "Not Found "+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("get-food-by-id/{foodId}")
	public ResponseEntity<?> getFoodById(
			@PathVariable("foodId") int foodId,
			@RequestHeader("Authorization") String token) throws Exception{
			    try {
			    	String jwt = token.split(" ")[1].trim();
			        User user = uService.findUserByJwtToken(jwt);
			        Foods foods = fServices.getFoodById(foodId);
					return 	new ResponseEntity<>(foods,HttpStatus.OK);
			    }catch(Exception e) {
			    	String errorMessage = "Failed to get Food!"+e.getMessage();
			    	return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			    }
	}
	
	

}
