package com.fooddeliveryapp.DTO;


import java.util.List;
import com.fooddeliveryapp.Model.Address;
import com.fooddeliveryapp.Model.OrderItem;

import lombok.Data;

@Data
public class orderRequest {
	
	private int restaurantId;
	
	private Address deliveryAddress;
	
	private List<OrderItem> items;
	
}
