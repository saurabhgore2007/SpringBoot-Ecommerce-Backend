package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDTO.UserResponse;
import com.ecommerce.entity.User;
import com.ecommerce.security.AuthUtil;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value="/me")
	public ResponseEntity<UserResponse> getCurrentUser(){
		
		String email = AuthUtil.getCurrentUserEmail();
        User user = userService.fetchByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(userService.toResponse(user));

	}
}
