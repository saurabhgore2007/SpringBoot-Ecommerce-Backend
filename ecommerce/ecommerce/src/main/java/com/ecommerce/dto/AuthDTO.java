package com.ecommerce.dto;

import com.ecommerce.entity.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {

	public record RegisterRequest(@NotBlank(message = "Email is required") @Email String email,
			                      @NotBlank(message = "Password is required") String password, 
			                      UserRole role) {}

	public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}

	public record AuthResponse(String token) {}
	
}
