package com.fooddeliveryapp.DTO;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class RestaurantDto {
	
     private String title;
     
     @Column(length = 1000)
     private List<String> images;
     
     private String description;
     
     private Long id;
}
