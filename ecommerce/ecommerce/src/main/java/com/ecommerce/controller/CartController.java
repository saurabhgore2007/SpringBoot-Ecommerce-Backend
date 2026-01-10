package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.dto.CartDTO.CartItemRequest;
import com.ecommerce.dto.CartDTO.CartResponse;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/cart") 
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<CartResponse> getCurrentCart(Authentication auth){
		
		String userEmail = auth.getName();
        CartResponse cart = cartService.getCartByUserEmail(userEmail);
        return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/items")
	public ResponseEntity<CartResponse> addItems(Authentication auth,@RequestParam Long productId,@RequestParam int qty){
		
		String email = auth.getName();
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return ResponseEntity.ok(cartService.addItem(user, productId, qty));
	}
	
	@PutMapping("/items/{itemId}")
	public ResponseEntity<CartResponse> updateItemQty(Authentication auth,@PathVariable Long itemId,@RequestBody @Valid CartItemRequest req){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		return ResponseEntity.ok(cartService.updateQty(user,itemId,req.quantity()));
	}
	
	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<CartResponse> deleteItem(Authentication auth,@PathVariable Long itemId){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		return ResponseEntity.ok(cartService.removeItem(user,itemId));
	}
	
	@DeleteMapping("/items/clear")
	public ResponseEntity<CartResponse> clearCart(Authentication auth){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		return ResponseEntity.ok(cartService.clearCart(user));
	}
}
