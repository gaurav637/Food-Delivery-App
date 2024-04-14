package com.fooddeliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddeliveryapp.Model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

}
