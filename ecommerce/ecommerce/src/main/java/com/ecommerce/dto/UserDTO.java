package com.ecommerce.dto;

import java.time.OffsetDateTime;

import com.ecommerce.entity.UserRole;


public class UserDTO {

	
	public record UserResponse(Long id,String email,UserRole role,OffsetDateTime  createdAt) {}
}
