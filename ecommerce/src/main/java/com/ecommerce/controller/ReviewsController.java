package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ReviewDTO.ReviewRequest;
import com.ecommerce.dto.ReviewDTO.ReviewResponse;
import com.ecommerce.service.ProductReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/products/{productId}/reviews")
public class ReviewsController {
	
	@Autowired
	private ProductReviewService productReviewService;
	
	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ReviewResponse> addReview(@PathVariable Long productId,@Valid @RequestBody ReviewRequest req,Principal  userDetails){
		
		ReviewResponse response = productReviewService.saveReview(productId, req, userDetails.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long productId){
		
		List<ReviewResponse> response = productReviewService.fetchByProduct(productId);
        return ResponseEntity.ok(response);
	}
}
