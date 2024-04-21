package com.fooddeliveryapp.ServicesImple;

import org.springframework.beans.factory.annotation.Autowired;
import com.fooddeliveryapp.DTO.addCartItemRequest;
import com.fooddeliveryapp.Exceptions.ResourceNotFoundException;
import com.fooddeliveryapp.Model.Cart;
import com.fooddeliveryapp.Model.CartItem;
import com.fooddeliveryapp.Model.Foods;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Repository.CartItemRepository;
import com.fooddeliveryapp.Repository.CartRepository;
import com.fooddeliveryapp.Services.CartServices;
import com.fooddeliveryapp.Services.FoodServices;
import com.fooddeliveryapp.Services.userService;

public class CartServiceImple implements CartServices{
	
	@Autowired
	private CartItemRepository cItemRepository;
	
	@Autowired
	private CartRepository cRepository;
	
	@Autowired
	private userService uService;
	
	@Autowired
	private FoodServices fServices;

	@Override
	public CartItem addItemToCart(addCartItemRequest req, String jwt) throws Exception {
		User user = uService.findUserByJwtToken(jwt);
		CartItem cartItem = new CartItem();
		Foods foods = fServices.getFoodById(req.getFoodId());
		Cart cart = cRepository.findByUserId(user.getId());	
		for(CartItem cartItem2 : cart.getCartItem()) {		
			if(cartItem2.getFoods().equals(foods)) {	
				int newQuantity = cartItem2.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem2.getId(),newQuantity);
				
			}
		}
		
	    cartItem.setFoods(foods);
	    cartItem.setCart(cart);
		cartItem.setQuantity(req.getQuantity());
		cartItem.setTotalPrice(req.getPrice()*foods.getPrice());
		cartItem.setIngredients(req.getIngredients());
		CartItem savedCartItem = cItemRepository.save(cartItem);
		cart.getCartItem().add(savedCartItem);
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(int cartItemId, int quantity) {
		
		CartItem cartItem = cItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem","id",cartItemId));
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(cartItem.getFoods().getPrice()*quantity);
		CartItem newItem = cItemRepository.save(cartItem);
		return newItem;
	}

	@Override
	public Cart removeItemFromCart(int cartItem, String jwt) throws Exception {
		
		User user = uService.findUserByJwtToken(jwt);
		Cart cart = cRepository.findByUserId(user.getId());
		CartItem cartItem2 = cItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("CartItem","Id",cartItem));
		if(cartItem2==null) {
			throw new Exception("cartItem is empty!");
		}
		cart.getCartItem().remove(cartItem);	
		Cart savedCart = cRepository.save(cart);
		return savedCart;
	}

	@Override
	public int calculateTotalCarts(Cart cart) {
		int total =0;
		for(CartItem cartItem:cart.getCartItem()) {
			total+=cartItem.getFoods().getPrice() * cartItem.getQuantity();	
		}
		return total;
	}

	@Override
	public Cart findCartById(int id) {
		Cart cart = cRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart","id",id));
		return cart;
	}

	@Override
	public Cart findCartByUserId(int id)throws Exception {
		User user = uService.getUserById(id);
		Cart cart = cRepository.findByUserId(id);
	    if(cart==null) {
	    	throw new Exception("cart is empty!!");
	    }
	    return cart;	
    }

	@Override
	public Cart clearCart(int userId)throws Exception {
		User user = uService.getUserById(userId);
		Cart cart = cRepository.findByUserId(userId);
		cart.getCartItem().clear();
		if(cart!=null) {
			throw new Exception("failed to clear cart!");		
		}
		return cRepository.save(cart);
	}

}
