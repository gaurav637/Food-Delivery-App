package com.fooddeliveryapp.Controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.DTO.RestaurantDto;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Request.CreateRestaurantRequest;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Response.ResponseWrapper;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api/admin/restaurant")
public class AdminRestaurantController {
	
	@Autowired
	private restaurantServices rServices;
	
	@Autowired
	private userService uService;
	
	@Autowired
	private userRepository uRepository;
	
	@PostMapping("/create")
	public ResponseEntity<?> createRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String str) throws Exception{
		
		try {	
			User user = uService.findUserByJwtToken(str);
			Restaurant restaurant = rServices.createRestaurant(req, user);
			return new ResponseEntity<Restaurant>(restaurant,HttpStatus.CREATED);
			
		}catch(Exception e) {
			String errorMessage = "Failed to create Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage ,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update-restaurant/{restId}")
	public ResponseEntity<?> updateRestaurants(@RequestBody CreateRestaurantRequest req, @PathVariable("restId") int restId){
		try {
			Restaurant restaurant = rServices.updateRestaurant(restId, req);
			return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to update Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
	
	@DeleteMapping("delete-restaurant/{restId}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable("restId") int restId){
		try {
			this.rServices.deleteRestaurant(restId);
			ApiResponse response = new ApiResponse("Restaurant is deleted successfully.",true);
			return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to Delete Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	public ResponseEntity<ResponseWrapper> getAllSearchRestaurants(@RequestParam("name") String name) throws Exception{
		try {
            List<Restaurant> allRestaurants = rServices.searchRestaurant(name);
            return ResponseEntity.ok(new ResponseWrapper(allRestaurants, "Valid"));
        } catch (Exception e) {
            String errorMessage = "Restaurant is not found with Restaurant name : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ResponseWrapper(null, errorMessage));
        }
		
	}
	
	@GetMapping("/get-restaurant-by-id/{restId}")
	public ResponseEntity<?> getRestaurantById(@PathVariable("restId") int id){
		try {
			Restaurant restaurant = rServices.getRestaurantById(id);
			return new ResponseEntity<>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage	= "Restaurant is not Present with Restaurant Id :"+e.getMessage();		
			return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get-restaurant-by-user-id/{userId}")
	public ResponseEntity<?> getRestaurantByUserId(@PathVariable("userId") int id){
		try {
			Restaurant restaurant = rServices.getRestaurantByUserId(id);
			return new ResponseEntity<>(restaurant,HttpStatus.OK);
		}catch(Exception e) {
			String  errorMessage = "Restaurant is not present with userId:"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/add-favorite-restaurant/{restId}/user/{userId}")
	public ResponseEntity<?> addFavoriteRestaurant(@PathVariable("restId") int restId,@PathVariable("userId") int userId){
		try {
			User user = uService.findUserById(userId);
			RestaurantDto restDto =	rServices.addFavoritesRestaurant(restId, user);
			return new ResponseEntity<>(restDto,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to add Favorite Restaurant !"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/update-restaurant-status/{restId}")
	public ResponseEntity<?> updateRestaurantStatusInController(@PathVariable("restId") int id){
		try {
			  
			Restaurant rest = rServices.updateRestaurantStatus(id);
			return new ResponseEntity<>(rest,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}catch(Exception e) {
			String errorMessage = "Failed to update Restaurant status!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);		
		}
	}
}
