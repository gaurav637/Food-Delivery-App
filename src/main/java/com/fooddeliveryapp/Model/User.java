package com.fooddeliveryapp.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooddeliveryapp.DTO.RestaurantDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String fullName;
	
	//@Email(message = "please enter valid email")
	private String email;
	
	//@Size(max = 12,min = 4,message = "password size 8 to 12")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY )
	private String password;
	
	private USER_ROLE roles = USER_ROLE.ROLE_USER;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
	private List<Orders>  orders = new ArrayList<>();
	
	@ElementCollection
	private List<RestaurantDto> favorite = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Address> address =  new ArrayList<>();
	
	@OneToMany
	private List<Restaurant> bookmarksRestaurants = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Orders> orderHistoryList = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Notification> userNotification = new ArrayList<>();
	
	

}
