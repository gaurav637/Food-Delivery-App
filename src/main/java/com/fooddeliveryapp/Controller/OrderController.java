package com.fooddeliveryapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fooddeliveryapp.DTO.orderRequest;
import com.fooddeliveryapp.Model.Orders;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.OrderServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api/v1")
public class OrderController {
	
	@Autowired
	private OrderServices oServices;
	
	@Autowired
	private userService uService;
	
	@PostMapping("user/create-order")
	public ResponseEntity<?> createOrderInController(
			@RequestBody orderRequest req,
			@RequestHeader("Authorization") String token) throws Exception{
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			Orders newOrders = this.oServices.createOrders(req, user);
			return new ResponseEntity<>(newOrders,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to create to order!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("user/update-order/{orderId}")
	public ResponseEntity<?> updateOrder(
			@PathVariable("orderId") int orderId,
			@RequestParam String status,
			@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			Orders updatedOrder = oServices.updatesOrders(orderId, status);
			return new ResponseEntity<>(updatedOrder,HttpStatus.OK);		
		}catch(Exception e) {
			String errorMessage = "Failed to update Order!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("user/delete-order/{orderId}")
	public ResponseEntity<ApiResponse> deleteOrder(
			@PathVariable("orderId") int orderId,
			@RequestHeader("Authorization") String token) throws Exception{
		String jwt = token.split(" ")[1].trim();
		User user = uService.findUserByJwtToken(jwt);
		ApiResponse response = oServices.deleteOrder(orderId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("user/get-user-orders")
	public ResponseEntity<?> getUsersOrdersInController(
			@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			List<Orders> allUserOrders = oServices.getUsersOrders(user.getId());
			return new ResponseEntity<>(allUserOrders,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "failed to get user orders!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("user/get-restaurant-orders/{restId}")
	public ResponseEntity<?> getRestaurantOrdersInController(
			@PathVariable("restId") int restId,
			@RequestParam String status,
			@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			List<Orders> allRestaurantOrders = oServices.getRestaurantOrders(restId, status);
			return new ResponseEntity<>(allRestaurantOrders,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to get Restaurant orders!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("user/get-order-by-id/{orderId}")
	public ResponseEntity<?> getOrderByIdInController(@PathVariable("orderId") int orderId,@RequestHeader("Authorization") String token){
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			Orders orders = oServices.getOrderById(orderId);
			return new ResponseEntity<>(orders,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to get Order by Id"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
