package com.fooddeliveryapp.Services;

import java.util.List;
import com.fooddeliveryapp.Model.IngredientsCategory;
import com.fooddeliveryapp.Model.IngredientsItem;

public interface IngredientsServices {
	
	public IngredientsCategory createIngredientsCategory(String name,int restaurantId);
	
	public IngredientsCategory findIngredientsCategoryById(int id);
	
	public List<IngredientsCategory> fidnIngredientsCategoryByRestaurantId(int id);
	
	public IngredientsItem createIngredientsItems(int restId,String ingredientsName,int categoryId);
	
	public List<IngredientsItem> findRestaurantIngredients(int restId);
	
	public IngredientsItem updateStock(int id);

}
