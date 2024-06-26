package com.fooddeliveryapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address {

	@Id
	private int id;
	
	private String streetAddress;
	
	private String city;
	
	private String stateProvince;
	
	private String postalCode;
	
	private String country;
	
	
	
}
