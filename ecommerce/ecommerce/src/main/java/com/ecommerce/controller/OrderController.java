package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDTO.OneOrderResponse;
import com.ecommerce.dto.OrderDTO.OrderCreateRequest;
import com.ecommerce.dto.OrderDTO.OrderResponse;
import com.ecommerce.dto.OrderDTO.OrderStatusUpdateRequest;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/orders")
public class OrderController {

//	@Autowired
//	private CartService cartService;
//	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> listOrders(Authentication auth){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	     	  
        return ResponseEntity.ok(orderService.listOrders(user));
	}
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(Authentication auth,@RequestBody @Valid OrderCreateRequest req){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	     	  
        return ResponseEntity.ok(orderService.createOrder(user,req));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OneOrderResponse> getOrderById(Authentication auth,@PathVariable @Valid  Long id){
		
		String email = auth.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	     	  
        return ResponseEntity.ok(orderService.getOrderById(user,id));
	}
	

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusUpdateRequest req) {
        orderService.updateOrderStatus(id,req.status());
        return ResponseEntity.noContent().build(); 
    }

}
