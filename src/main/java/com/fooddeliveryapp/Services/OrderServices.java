package com.fooddeliveryapp.Services;

import java.util.List;

import com.fooddeliveryapp.DTO.orderRequest;
import com.fooddeliveryapp.Model.Orders;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Response.ApiResponse;

public interface OrderServices {
	
	public Orders createOrders(orderRequest request,User user) throws Exception;
	
	public Orders updatesOrders(int orderId,String orderStatus) throws Exception;
	
	public ApiResponse deleteOrder(int orderId);
	
	public List<Orders> getUsersOrders(int userId);
	
	public List<Orders> getRestaurantOrders(int restId,String orderStatus);
	
	public Orders getOrderById(int orderId);
 
}
