package com.fooddeliveryapp.ServicesImple;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.CategoryRepository;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.CategoryServices;
import com.fooddeliveryapp.Services.restaurantServices;
import com.fooddeliveryapp.Services.userService;


@Service
public class CategoryServicesImple implements CategoryServices{
	
	@Autowired
	private userService uService;
	
	@Autowired
	private CategoryRepository cRepository;
	
	@Autowired
	private restaurantServices rest;

	@Override
	public Category createCategory(Category name, int userId) throws Exception {
		Category category = new Category();
		User user = uService.getUserById(userId);
		Restaurant restaurant = rest.getRestaurantByUserId(userId);
		category.setName(name.getName());
		category.setRestaurant(restaurant);
		Category newCate = cRepository.save(category);
		return newCate;
	}

	@Override
	public List<Category> findCategoriesByRestaurantId(int restId) {
		Restaurant restaurant = rest.getRestaurantById(restId);
		List<Category> restaurants = cRepository.findByRestaurantId(restaurant.getId());	
		return restaurants;
	}

	@Override
	public Category findCategoryById(int id) {
		Category category = cRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Id",id)); 
		return category;
	}

	@Override
	public ApiResponse deleteCategory(int id) {
		Category category = cRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Id",id)); 
		cRepository.delete(category);
		ApiResponse response = new ApiResponse("category id deleted successfully.",true);
		return response;	
	}

}
