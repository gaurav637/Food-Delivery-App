package com.fooddeliveryapp.ServicesImple;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.IngredientsCategory;
import com.fooddeliveryapp.Model.IngredientsItem;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Repository.IngredientsCategoryRepository;
import com.fooddeliveryapp.Repository.IngredientsItemRepository;
import com.fooddeliveryapp.Services.IngredientsServices;
import com.fooddeliveryapp.Services.restaurantServices;


@Service
public class IngredientsServiceImple implements IngredientsServices{
	
	@Autowired
	private IngredientsCategoryRepository icRepository;
	
	@Autowired
	private IngredientsItemRepository IiRepository;
	
	@Autowired
	private restaurantServices rest;
	
	@Autowired
	private IngredientsServices iServices;
	
	

	
	@Override
	public IngredientsCategory createIngredientsCategory(String name, int restaurantId) {
		Restaurant restaurant = rest.getRestaurantById(restaurantId);
		IngredientsCategory ingredientsCategory = new IngredientsCategory();
		ingredientsCategory.setName(name);
		ingredientsCategory.setRestaurant(restaurant);
		IngredientsCategory ansCategory = new IngredientsCategory();
		ansCategory = icRepository.save(ingredientsCategory);
		return ansCategory;
	}

	@Override
	public IngredientsCategory findIngredientsCategoryById(int id) {
		IngredientsCategory iCategory = icRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("IngredientsCategory","Id",id));
		return iCategory;
	}

	@Override
	public List<IngredientsCategory> fidnIngredientsCategoryByRestaurantId(int id) {
		Restaurant restaurant = rest.getRestaurantById(id);
		List<IngredientsCategory> ingredientsCategories = icRepository.findByRestaurantId(id);
		return ingredientsCategories;
	}

	@Override
	public IngredientsItem createIngredientsItems(int restId, String ingredientsName, int categoryId) {
		IngredientsItem ingredientsItem = new IngredientsItem();
		Restaurant restaurant = rest.getRestaurantById(restId);
		IngredientsCategory category = iServices.findIngredientsCategoryById(categoryId);
	    ingredientsItem.setCategory(category);
	    ingredientsItem.setName(ingredientsName);
	    ingredientsItem.setRestaurant(restaurant);
	    IngredientsItem idg = new IngredientsItem();
	    idg = IiRepository.save(ingredientsItem);
		return idg;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(int restId) {
		List<IngredientsItem> ansItems = IiRepository.findByRestaurantId(restId);
		return ansItems;
	}

	@Override
	public IngredientsItem updateStock(int id) {
		IngredientsItem idg1 = IiRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Ingredients","Id",id));
		if(idg1.isInStock()) {
			idg1.setInStock(false);
		}else {
			idg1.setInStock(true);
		}
		IngredientsItem newUpdatedIngredientsItem = IiRepository.save(idg1);
		return newUpdatedIngredientsItem;	
	}

}
