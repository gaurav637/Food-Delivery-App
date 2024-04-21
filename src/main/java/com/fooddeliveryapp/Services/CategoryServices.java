package com.fooddeliveryapp.Services;

import java.util.List;

import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Response.ApiResponse;

public interface CategoryServices {
	
	public Category createCategory(Category name,int userId)throws Exception;
	
	public List<Category> findCategoriesByRestaurantId(int restId);
	
	public Category findCategoryById(int id);
	
	public ApiResponse deleteCategory(int id);
	
	

}
