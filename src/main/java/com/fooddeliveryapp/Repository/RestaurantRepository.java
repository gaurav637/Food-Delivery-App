package com.fooddeliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fooddeliveryapp.Model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	
	
    @Query(value = "SELECT * FROM restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(r.cuisine_type) LIKE LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
	public List<Restaurant> getAllSearchRestaurants(@Param("name")String name);
	
	public Restaurant findByOwnersId(int id);

}
