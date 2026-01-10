package com.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.dto.AuthDTO.AuthResponse;
import com.ecommerce.dto.AuthDTO.LoginRequest;
import com.ecommerce.dto.AuthDTO.RegisterRequest;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
	
  private final UserService userService;
  private final JwtUtil jwtUtil; 
  //private final PasswordEncoder encoder; 
 
  public AuthController(UserService userService, JwtUtil jwtUtil) {
      this.userService = userService;
      this.jwtUtil = jwtUtil;
      //this.encoder = encoder;
  }

  
  @PostMapping("/register") 
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
	  
    var user = userService.register(req.email(), req.password(),req.role());
    
    return ResponseEntity.status(HttpStatus.CREATED).body(
    		new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole().name()))); 
  } 
 
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
	  
      var user = userService.authenticate(req.email(), req.password()); 
      
      return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole().name())));
  }
  
}