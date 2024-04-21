package com.fooddeliveryapp.DTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Model.IngredientsItem;
import lombok.Data;

@Data
public class CreateFoodRequest {

	private String name;
	
	private String description;
	
	private Long price;
	
	private Category foodCategory;
	
	private List<String> images;
	
	private boolean available;
	
	private int restId;
	
	private boolean isVegetarian;
	
	private boolean isSeasonal;
	
	private List<IngredientsItem> ingredients;
	
	private Date createDate;
	

}
