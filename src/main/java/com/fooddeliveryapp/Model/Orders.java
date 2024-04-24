package com.fooddeliveryapp.Model;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Orders {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private User customer;
	
	@ManyToOne
	private Restaurant restaurant;
	
	private Long totalAmmount;
	
	private String orderStatus;
	
	private Date createdAT;
	
	@ManyToOne
	private Address deliveryAddress;
	
	@OneToMany
	private List<OrderItem> items;
	
	//private Payments payment;
	
	private int totalItem;
	
	private long totalPrice;
	

}
