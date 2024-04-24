package com.fooddeliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddeliveryapp.Model.Foods;

public interface FoodRepository extends JpaRepository<Foods, Integer> {
	
    @Query(value = "SELECT * FROM foods f WHERE LOWER(f.keyword) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.category.name) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
	public List<Foods> getSeachesFoods(@Param(value = "keyword") String keyword);

}
//@Query(value = "SELECT * FROM foods f WHERE LOWER(f.keyword) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
//public List<Foods> getSeachesFoods(@Param("keyword") String keyword);
