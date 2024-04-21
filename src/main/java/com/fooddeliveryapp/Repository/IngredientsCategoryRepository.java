package com.fooddeliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.IngredientsCategory;

public interface IngredientsCategoryRepository extends JpaRepository<IngredientsCategory, Integer> {
	
	public List<IngredientsCategory> findByRestaurantId(int id);

}
