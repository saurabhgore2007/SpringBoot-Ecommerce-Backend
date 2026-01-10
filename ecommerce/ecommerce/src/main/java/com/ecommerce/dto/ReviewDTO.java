package com.ecommerce.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ReviewDTO {

	public record ReviewRequest(@Min(0) @Max(5) int rating,String comment) {}

	public record ReviewResponse(Long id,int rating,String comment,String userEmail,OffsetDateTime  createdAt) {}
}
