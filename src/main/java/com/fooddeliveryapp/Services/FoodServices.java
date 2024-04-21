package com.fooddeliveryapp.Services;

import java.util.List;
import com.fooddeliveryapp.DTO.CreateFoodRequest;
import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Model.Foods;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Response.ApiResponse;

public interface FoodServices {
	
	Foods createFood(CreateFoodRequest request,Category category,Restaurant restaurant);
	
    List<Foods> getRestaurantFoods(int restId, 
    		boolean isVagetarian,
    		boolean isNonVage,
    		boolean isSeasonal,
    		String foodCategory);
    
    Foods getFoodById(int foodId);
    
    ApiResponse deleteFood(int foodId);
    
    List<Foods> searchFood(String keyword);
    
    Foods updateAvailabilityStatus(int  foodId);
    
    
    

}
