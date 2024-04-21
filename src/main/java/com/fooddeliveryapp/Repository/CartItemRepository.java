package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddeliveryapp.Model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
