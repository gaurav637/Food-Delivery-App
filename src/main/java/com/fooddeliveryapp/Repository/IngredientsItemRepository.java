package com.fooddeliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.IngredientsItem;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Integer> {

	public List<IngredientsItem> findByRestaurantId(int id);
}
