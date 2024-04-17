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
import com.fooddeliveryapp.DTO.RestaurantDto;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Response.ResponseWrapper;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("/api/rastaurant")
public class customerRestaurantController {
	
	@Autowired
	private restaurantServices rServices;
	
	@Autowired
	private userService uService;
	
	@Autowired
	private userRepository uRepository;
	
	
	@GetMapping("/get-all-restaurants")
	public ResponseEntity<ResponseWrapper> getAllRestaurants() throws Exception{
		try {
            List<Restaurant> allRestaurants = rServices.getAllRestaurant();
            return ResponseEntity.ok(new ResponseWrapper(allRestaurants, "Valid"));
        } catch (Exception e) {
            String errorMessage = "Restaurant is Not Exist : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ResponseWrapper(null, errorMessage));
        }
	}
	
	@GetMapping("/get-all-search-restaurants")
	public ResponseEntity<ResponseWrapper> getAllSearchRestaurants(@RequestParam("name") String name,@RequestHeader("Authorization") String token) throws Exception{
		try {
            List<Restaurant> allRestaurants = rServices.searchRestaurant(name);
            User user = uService.findUserByJwtToken(token);          
            return ResponseEntity.ok(new ResponseWrapper(allRestaurants, "Valid"));
        } catch (Exception e) {
            String errorMessage = "Restaurant is not found with Restaurant name : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ResponseWrapper(null, errorMessage));
        }
		
	}
	
	@GetMapping("/get-restaurant-by-id/{restId}")
	public ResponseEntity<?> getRestaurantById(@PathVariable("restId") int id,@RequestHeader("Authorization") String token){
		try {
			Restaurant restaurant = rServices.getRestaurantById(id);
			User user = uService.findUserByJwtToken(token);
			return new ResponseEntity<>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage	= "Restaurant is not Present with Restaurant Id :"+e.getMessage();		
			return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/add-favorite-restaurant/{restId}/user/{userId}")
	public ResponseEntity<?> addFavoriteRestaurant(@PathVariable("restId") int restId,@PathVariable("userId") int userId,@RequestHeader("Authorization") String token){
		try {
			User user = uService.findUserByJwtToken(token);
			RestaurantDto restDto =	rServices.addFavoritesRestaurant(restId, user);
			return new ResponseEntity<>(restDto,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to add Favorite Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
