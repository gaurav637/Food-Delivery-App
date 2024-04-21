package com.fooddeliveryapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddeliveryapp.DTO.addCartItemRequest;
import com.fooddeliveryapp.DTO.updateCartQuantity;
import com.fooddeliveryapp.Model.Cart;
import com.fooddeliveryapp.Model.CartItem;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Services.CartServices;
import com.fooddeliveryapp.Services.userService;

@RestController
@RequestMapping("api/v1")
public class CartController {
	
	@Autowired
	private CartServices ctServices;
	
	@Autowired
	private userService uService;
	
	@PostMapping("user/add-item-to-cart")
	public ResponseEntity<?> addItemToCard(
			@RequestBody addCartItemRequest request,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			CartItem cartItem = ctServices.addItemToCart(request, jwt);
			return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
		}catch(Exception e) {
			String errorMessage = "Failed to add item to card !"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("user/update-cartItem-quantity")
	public  ResponseEntity<?> updateCardItemQuantityInController(
			@RequestBody updateCartQuantity req,
			@RequestHeader("Authorization") String token){
		
           try {	
        	   String jwt = token.split(" ")[1].trim();
        	   User user = uService.findUserByJwtToken(jwt);
       		   CartItem cartItem = ctServices.updateCartItemQuantity(req.getCartItem(), req.getQuantity());
       		   return new ResponseEntity<>(cartItem,HttpStatus.OK);
           }catch(Exception e) {
        	   String errorMessage = "Failed to update cart item quantity!"+e.getMessage();
        	   return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
           }
		
	}
	
	@DeleteMapping("user/remove-item-from-cart/{cartId}")
	public ResponseEntity<?> RemoveItemFromCartInController(
			@PathVariable("cartId") int id,
			@RequestHeader("Authorization") String token) {
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			Cart cart = ctServices.removeItemFromCart(id, jwt);
			return new ResponseEntity<>(cart,HttpStatus.OK);
			
		}catch(Exception e) {
			String errorMessage = "Failed to remove item from cart!!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(user/calcul)
	public ResponseEntity<?> calculateTotalCartsInController(
			@RequestBody Cart cart,
			@RequestHeader("Authorization") String token){
		
		try {
			String jwt = token.split(" ")[1].trim();
			User user = uService.findUserByJwtToken(jwt);
			int cart2 = ctServices.calculateTotalCarts(cart);
			return new ResponseEntity<>(cart2,HttpStatus.OK);
		}catch(Exception e) {
			String errorMessage = "Failed to calculate total cart!!"+e.getMessage();
			return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
