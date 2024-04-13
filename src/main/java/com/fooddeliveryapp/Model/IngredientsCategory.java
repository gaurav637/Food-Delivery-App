package com.fooddeliveryapp.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
	
	private String name;
	
	@ManyToOne
	@JsonIgnore
	private Restaurant restaurant;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<IngredientsItem> ingredients = new ArrayList<>();
	
	
     
     

}
