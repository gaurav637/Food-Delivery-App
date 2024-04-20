package com.fooddeliveryapp.Services;

import java.util.List;

import com.fooddeliveryapp.DTO.RestaurantDto;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Request.CreateRestaurantRequest;

public interface restaurantServices {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurant(int id,CreateRestaurantRequest updatedRestaurant);
	
	public boolean deleteRestaurant(int id);
	
	public List<Restaurant> getAllRestaurant()throws Exception;
	
	public List<Restaurant> searchRestaurant(String name)throws Exception;
	
	public Restaurant getRestaurantById(int id);
	
	public Restaurant getRestaurantByUserId(int id)throws Exception;
	
	public RestaurantDto addFavoritesRestaurant(int restId,User user);
	
	public Restaurant updateRestaurantStatus(int id);

}
