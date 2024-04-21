package com.fooddeliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	public List<Category> findByRestaurantId(int id);

}
