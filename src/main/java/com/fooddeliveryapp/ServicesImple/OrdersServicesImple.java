package com.fooddeliveryapp.ServicesImple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddeliveryapp.DTO.orderRequest;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.Address;
import com.fooddeliveryapp.Model.Cart;
import com.fooddeliveryapp.Model.CartItem;
import com.fooddeliveryapp.Model.OrderItem;
import com.fooddeliveryapp.Model.Orders;
import com.fooddeliveryapp.Model.Restaurant;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.AddressRepository;
import com.fooddeliveryapp.Repository.CartItemRepository;
import com.fooddeliveryapp.Repository.CartRepository;
import com.fooddeliveryapp.Repository.OrderRepository;
import com.fooddeliveryapp.Repository.RestaurantRepository;
import com.fooddeliveryapp.Repository.orderItemRepository;
import com.fooddeliveryapp.Repository.userRepository;
import com.fooddeliveryapp.Response.ApiResponse;
import com.fooddeliveryapp.Services.CartServices;
import com.fooddeliveryapp.Services.OrderServices;

@Service
public class OrdersServicesImple implements OrderServices{
	
	@Autowired
	private OrderRepository oRepository;
	
	@Autowired
	private userRepository uRepository;
	
	@Autowired
	private orderItemRepository oIRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private RestaurantRepository restRepository;
	
	@Autowired
	private CartServices cartServices;
	
	@Autowired
	private CartRepository ctRepository;
	
	@Autowired
	private CartItemRepository crtRepository;
	
	@Override
	public Orders createOrders(orderRequest request, User user) throws Exception {
		Orders orders = new Orders();
		//Cart c1 = request.getCart();
		//ctRepository.save(c1);
		Address deliveryAddress = request.getDeliveryAddress();
		Address address = addressRepository.save(deliveryAddress);
		if(!user.getAddress().contains(address)) {
			user.getAddress().add(address);
			uRepository.save(user);
		}
		
		Restaurant restaurant = restRepository.findById(request.getRestaurantId()).orElseThrow(()-> new ResourceNotFoundException("Restaurant","Id",request.getRestaurantId()));
		orders.setCreatedAT(new Date());
		orders.setCustomer(user);
		orders.setDeliveryAddress(address);
		orders.setOrderStatus("PENDING");
		orders.setRestaurant(restaurant);
		
		List<OrderItem> cartItems = new ArrayList<>();
	    for (OrderItem orderItem : request.getItems()) {
	        OrderItem cartItem = new OrderItem();
	        cartItem.setFoods(orderItem.getFoods());
	        cartItem.setQuantity(orderItem.getQuantity());
	        cartItem.setTotalPrice(orderItem.getTotalPrice());
	        cartItem.setIngredients(orderItem.getIngredients());
	        
	        // Set other properties of CartItem as needed
	        
	        // Add the CartItem to the list of CartItems
	        cartItems.add(cartItem);
	    }
		
		orders.setItems(cartItems);
		
		//Cart cart = cartServices.findCartByUserId(user.getId());
		//List<OrderItem> orderItems = new ArrayList<>();
		
//		for(CartItem cartItem: c1.getCartItem()) {
//			
//			OrderItem oItem = new OrderItem();
//			
//			oItem.setFoods(cartItem.getFoods());
//			oItem.setQuantity(cartItem.getQuantity());
//			oItem.setTotalPrice(cartItem.getTotalPrice());
//			oItem.setIngredients(cartItem.getIngredients());
//			OrderItem savedItem = oIRepository.save(oItem);
//			orderItems.add(savedItem);
//		}
//		
//		int totalPrice = cartServices.calculateTotalCarts(cart);		
//		orders.setItems(orderItems);
//		orders.setTotalPrice(totalPrice);
		
		
		Orders orders2 = oRepository.save(orders);
		return orders2;
	}

	@Override
	public Orders updatesOrders(int orderId, String orderStatus)throws Exception {
		Orders order = oRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Orders","Id",orderId));
		if(orderStatus.equals("PENDING")
				|| orderStatus.equals("COMPLETED")
				|| orderStatus.equals("DELIVERED")
				|| orderStatus.equals("UNDELIVERD")
		  ) {
			order.setOrderStatus(orderStatus);
			Orders savedOrders = oRepository.save(order);
			return savedOrders;
		}
		throw new Exception("Please select valid order status");
	}

	@Override
	public ApiResponse deleteOrder(int orderId) {
		Orders order = oRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Orders","Id",orderId));
        oRepository.delete(order);
        ApiResponse response = new ApiResponse("Order deleted successfully..",true);
		return response;
	}

	
	public List<Orders> getUsersOrders(int userId) {
		List<Orders> allUsersOrders = oRepository.findByRestaurantId(userId);
		return allUsersOrders;
	}

	
	public List<Orders> getRestaurantOrders(int restId, String orderStatus) {
		List<Orders> allRestaurantOrders = oRepository.findByRestaurantId(restId);	
		if(orderStatus!=null) {
			allRestaurantOrders = allRestaurantOrders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());	
		}
		return allRestaurantOrders;
	}

	@Override
	public Orders getOrderById(int orderId) {
        Orders order = oRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","Id",orderId));
		return order;
	}

}
