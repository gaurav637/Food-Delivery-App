package com.fooddeliveryapp.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Model.IngredientsCategory;
import com.fooddeliveryapp.Model.IngredientsItem;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Services.IngredientsServices;
import com.fooddeliveryapp.Services.userService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class IngredientsController {
	
	@Autowired
	private IngredientsServices iServices;
	
	@Autowired
	private userService uService;
	
	
	@PostMapping("/admin/create-ingredients-category/{restId}")
	public ResponseEntity<?> createIngredientsCategory(
			@RequestParam String name, 
			@PathVariable("restId") int id,
			@RequestHeader("Authorization") String token) throws Exception{
		
		try {
			String jwt = token.split(" ")[1].trim();
			//User user = uService.findUserByJwtToken(jwt);
			
			IngredientsCategory ingredients = iServices.createIngredientsCategory(name, id);
			return new ResponseEntity<>(ingredients,HttpStatus.CREATED);
		}catch(Exception e) {
			String errorMessage = "Failed to create new Ingredients Category!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("user/find-ingredients-by-id/{inCateId}")
	public ResponseEntity<?> findIngredientsByIdInController(
			        @PathVariable("inCateId") int inCateId ,
					@RequestHeader("Authorization") String token){
		
		   try {
			   String jwt = token.split(" ")[1].trim();
			   User user = uService.findUserByJwtToken(jwt);
			   IngredientsCategory ingredientsCategory = iServices.findIngredientsCategoryById(inCateId);
			   return new ResponseEntity<>(ingredientsCategory,HttpStatus.OK);
		   }catch(Exception e) {
			   String errorMessage = "not Found "+e.getMessage();
			   return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		   }
	}
	
	@GetMapping("user/find-ingredients-by-restaurant-id/{restId}")
	public ResponseEntity<?> findInCategoryByRestId(
			@PathVariable("restId") int restId,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			List<IngredientsCategory> ingredients = iServices.fidnIngredientsCategoryByRestaurantId(restId);
			return new ResponseEntity<>(ingredients,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Not Found any ingredients with restaurant id !"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("admin/create-ingredientsitem-restid/{restId}/category-id/{InCateId}")
	public ResponseEntity<?> createIngredientsItemInController(
			@PathVariable("restId") int restId,
			@RequestParam String name,
			@PathVariable("InCateId") int InCateId,
	        @RequestHeader("Authorization") String token){
		
			try {
				String jwt = token.split(" ")[1].trim();
				User user = uService.findUserByJwtToken(jwt);
				IngredientsItem ingredientsItem = iServices.createIngredientsItems(restId, name, InCateId);
				return new ResponseEntity<>(ingredientsItem,HttpStatus.OK);	
				
			}catch(Exception e) {
				String errorMessage = " Failed to create Ingredients Items"+e.getMessage();			
				return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("user/find-all-ingredients-in-restaurant/{restId}")
	public ResponseEntity<?> findRestaurantIngredientsInController(
			@PathVariable("restId") int restId,
			@RequestHeader("Authorization") String token) throws Exception{
		
		   try {
			   String jwt = token.split(" ")[1].trim();
			   User user = uService.findUserByJwtToken(jwt);
			   
			   List<IngredientsItem> allRestaurantIngredients = iServices.findRestaurantIngredients(restId);
			   return new ResponseEntity<>(allRestaurantIngredients,HttpStatus.OK);
			
		   }catch(Exception e) {
			   String errorMessage = "Failed to find ingredients with restaurant "+e.getMessage();
			   return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
		   }
		
	}
	
	@PutMapping("admin/update/stock/status/{id}")
	public ResponseEntity<?> updateStockStatus(@PathVariable("id") int id,@RequestHeader("Authorization") String token){
		
		 try {
			 String jwt = token.split(" ")[1].trim();
			 User user = uService.findUserByJwtToken(jwt);
			 IngredientsItem ingredientsItem = iServices.updateStock(id);
			 return new ResponseEntity<>(ingredientsItem,HttpStatus.OK);
		 }catch(Exception e) {
			 String errorMessage = "Failed to update stock !"+e.getMessage();
			 return new ResponseEntity<>(errorMessage,HttpStatus.NOT_MODIFIED);
		 }
	}
	

}
