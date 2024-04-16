package com.fooddeliveryapp.Request;

import java.util.ArrayList;
import java.util.List;
import com.fooddeliveryapp.Model.Address;
import com.fooddeliveryapp.Model.Contact;
import com.fooddeliveryapp.Model.User;
import lombok.Data;

@Data
public class CreateRestaurantRequest {
	
	
	private Long id;
	
	private User owners;
	
	private String name;
	
	private String description;
	
	private String cuisineType;
	
	private Address address;

	private Contact contactInfo;
	
	private String openingHours;
	
	private String reviews;
	
	private Double rating;
	
	private List<String> images = new ArrayList<>();
	
	
}
