package com.fooddeliveryapp.Services;

import com.fooddeliveryapp.DTO.addCartItemRequest;
import com.fooddeliveryapp.Model.Cart;
import com.fooddeliveryapp.Model.CartItem;

public interface CartServices {
	
	public CartItem addItemToCart(addCartItemRequest req,String jwt)throws Exception;
	
	public CartItem updateCartItemQuantity(int cartItemId,int quantity)throws Exception;
	
	public Cart removeItemFromCart(int cartItem,String jwt) throws Exception;
	
	public int calculateTotalCarts(Cart cart);
	
	public Cart findCartById(int id)throws Exception;
	
	public Cart findCartByUserId(int id) throws Exception;
	
	public Cart clearCart(int userId)throws Exception;

}
