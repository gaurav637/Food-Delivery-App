package com.fooddeliveryapp.DTO;

import com.fooddeliveryapp.Model.Address;
import lombok.Data;

@Data
public class orderRequest {
	
	private int restaurantId;
	
	private Address deliveryAddress;

}
