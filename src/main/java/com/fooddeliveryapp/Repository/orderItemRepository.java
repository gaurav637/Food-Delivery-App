package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddeliveryapp.Model.OrderItem;

public interface orderItemRepository extends JpaRepository<OrderItem, Integer>{

}
