package com.fooddeliveryapp.DTO;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
	
     private String title;
     
     @Column(length = 1000)
     private List<String> images;
     
     private String description;
     
     private int id;
}
