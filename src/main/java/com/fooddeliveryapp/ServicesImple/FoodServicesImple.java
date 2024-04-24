package com.fooddeliveryapp.ServicesImple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.DTO.CreateFoodRequest;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Model.Foods;
import com.fooddeliveryapp.Model.IngredientsCategory;
import com.fooddeliveryapp.Model.IngredientsItem;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Repository.CategoryRepository;
import com.fooddeliveryapp.Repository.FoodRepository;
import com.fooddeliveryapp.Repository.IngredientsCategoryRepository;
import com.fooddeliveryapp.Repository.IngredientsItemRepository;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.CategoryServices;
import com.fooddeliveryapp.Services.FoodServices;

@Service
public class FoodServicesImple implements FoodServices {
	
	@Autowired
	private FoodRepository fRepository;
	
	@Autowired
	private CategoryRepository cRepository;
	
	@Autowired
	private IngredientsItemRepository iItemRepository;
		
	@Autowired
	private IngredientsCategoryRepository inCateRepository;
	

//	public Foods createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
//	    Category savedCategory = cRepository.save(category);
//	    List<IngredientsItem> savedItems = new ArrayList<>();
//	    for (IngredientsItem item : request.getIngredients()) {
//	        //item.setCategory(savedCategory); 
//	        savedItems.add(iItemRepository.save(item)); 
//	    }
//	    Foods newFood = new Foods();
//	    newFood.setVegetarian(request.isVegetarian());
//	    newFood.setSeasonal(request.isSeasonal());
//	    newFood.setPrice(request.getPrice());
//	    newFood.setName(request.getName());
//	    newFood.setIngredients(savedItems); 
//	    newFood.setImages(request.getImages());
//	    newFood.setFoodCategory(savedCategory); 
//	    newFood.setDescription(request.getDescription());
//	    newFood.setCreateDate(new Date());
//	    newFood.setAvailable(request.isAvailable());
//	    newFood.setRestaurant(restaurant);
//	    restaurant.getFoods().add(newFood);
//	    Foods food = fRepository.save(newFood);
//	    return food;
//	}
	  List<String> nameList = new ArrayList<>();
      public Foods createFood(CreateFoodRequest request, Category category, Restaurant restaurant) throws Exception {
    	 
	    List<Category> ct = new ArrayList<Category>();
	    if(nameList.contains(request.getName()) && ct.contains(request.getFoodCategory())) {
	    	throw new Exception("Food is already present in restaurant");
	    	
	    }
	    nameList.add(request.getName());
	    ct.add(request.getFoodCategory());
		Category savedCategory = cRepository.save(category);
		//List<IngredientsItem> items = request.getIngredients();
		//List<IngredientsItem> inCate = request.getIngredients();
		
		//iItemRepository.saveAll(items);
		Foods newFood = new Foods();
		newFood.setVegetarian(request.isVegetarian());
		newFood.setSeasonal(request.isSeasonal());
		newFood.setPrice(request.getPrice());
		newFood.setName(request.getName());
		newFood.setIngredients(request.getIngredients());
		newFood.setImages(request.getImages());
		newFood.setFoodCategory(request.getFoodCategory());
		newFood.setDescription(request.getDescription());	
		newFood.setCreateDate(new Date());
		newFood.setAvailable(request.isAvailable());
		newFood.setFoodCategory(savedCategory);
		newFood.setRestaurant(restaurant);
		restaurant.getFoods().add(newFood);
		Foods food = fRepository.save(newFood);
		return food;
	} 

	@Override
	public List<Foods> getRestaurantFoods(int restId, boolean isVagetarian1, boolean isNonVage1, boolean isSeasonal1,
			String foodCategory1) {
		
		List<Foods> ansFoods = new ArrayList<>();
		List<Foods> ans = fRepository.findAll();
		for(Foods food:ans) {
			if(food.isVegetarian()==isVagetarian1 && 
					food.isSeasonal()==isSeasonal1 && 
					food.getFoodCategory().getName() == foodCategory1&&
					food.getRestaurant().getId()==restId) {
				
				ansFoods.add(food);		
				
			}
		}
		
		return ansFoods;
	}

	@Override
	public Foods getFoodById(int foodId) {
		Foods foods = fRepository.findById(foodId).orElseThrow(()-> new ResourceNotFoundException("Foods","FoodId",foodId));
		return foods;
	}

	@Override
	public ApiResponse deleteFood(int foodId) {
		Foods foods = fRepository.findById(foodId).orElseThrow(()-> new ResourceNotFoundException("Foods","FoodId",foodId));
        try {
        	 foods.setRestaurant(null);
        	 fRepository.delete(foods);
     		//fRepository.save(foods);
     		ApiResponse response = new ApiResponse("Food is deleted successfully.",true); 
     		return response;     	
     	}catch (Exception e) {
     		ApiResponse response = new ApiResponse("failes to delete food.",false);
     		return response;		
     	}
		
	}

	@Override
	public List<Foods> searchFood(String keyword) {
		List<Foods> allFoods = fRepository.getSeachesFoods(keyword);
		return allFoods;
	}

	@Override
	public Foods updateAvailabilityStatus(int foodId) {
		Foods foods = fRepository.findById(foodId).orElseThrow(()-> new ResourceNotFoundException("Foods","FoodId",foodId));
        foods.setAvailable(!foods.isAvailable());
        Foods ansFoods = fRepository.save(foods);
		return ansFoods;
	}

}
