package com.fooddeliveryapp.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{
	
	public List<Orders> findByCustomerId(int userId);
	
	public List<Orders> findByRestaurantId(int restId);

}
