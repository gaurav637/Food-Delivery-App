package com.fooddeliveryapp.DTO;

import java.util.List;
import lombok.Data;

@Data
public class addCartItemRequest {
	
	private int foodId;
	
	private int quantity;
	
	private List<String> ingredients;

}
