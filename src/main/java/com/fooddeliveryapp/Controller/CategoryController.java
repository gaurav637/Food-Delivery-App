package com.fooddeliveryapp.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.Model.Category;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.CategoryServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api")
public class CategoryController {
	
	@Autowired
	private CategoryServices cServices;
	
	@Autowired
	private userService uService;
	
	@PostMapping("v1/admin/create-new-food-category")
	public ResponseEntity<?> createCategory(
			@RequestBody Category category,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
			Category category2 = cServices.createCategory(category, user.getId());
			return new ResponseEntity<>(category2,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to create new Category!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("v1/user/get-restaurant-food-category")
	public ResponseEntity<?> getRestaurantCategory(
			@PathVariable("restId") int restId,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
			List<Category> category2 = cServices.findCategoriesByRestaurantId(restId);
			return new ResponseEntity<>(category2,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to get Restaurant category!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("v1/user/get-food-category")
	public ResponseEntity<?> getCategory(
			@PathVariable("categoryId") int categoryId,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
		    Category category2 = cServices.findCategoryById(categoryId);
			return new ResponseEntity<>(category2,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to get category!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("v1/admin/delete-food-category")
	public ResponseEntity<?> deleteCategory(
			@PathVariable("categoryId") int categoryId,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(token);
		    ApiResponse response = cServices.deleteCategory(categoryId);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to delete Category!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
