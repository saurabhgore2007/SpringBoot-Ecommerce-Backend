package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ReviewDTO.ReviewRequest;
import com.ecommerce.dto.ReviewDTO.ReviewResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductReview;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ProductReviewRepository;
import com.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductReviewService {

	@Autowired
	private ProductReviewRepository productReviewRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public ReviewResponse saveReview(Long productId, ReviewRequest req, String userEmail) {
	    Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	    User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    ProductReview review = new ProductReview(
	        req.rating(),
	        req.comment(),
	        product,
	        user
	    );

	    ProductReview saved = productReviewRepo.save(review);

	    return new ReviewResponse(
	        saved.getId(),
	        saved.getRating(),
	        saved.getComment(),
	        user.getEmail(),
	        saved.getCreatedAt()
	    );
	}
	
	@Transactional
	public ProductReview updateRview(ProductReview review) {
		
		if(!productReviewRepo.existsById(review.getId())) {
			throw new ResourceNotFoundException("Review Not foundTo Update");
		}
		return productReviewRepo.save(review);
	}
	
	@Transactional
	public void deleteReview(Long id) {
        if (!productReviewRepo.existsById(id)) {
        	throw new ResourceNotFoundException("Review Not found To Delete");
        }
        productReviewRepo.deleteById(id);
    }
	
	@Transactional
	public List<ProductReview> fetchAll() {
        return productReviewRepo.findAll();
    }
	
	private ReviewResponse toResponse(ProductReview review) {
	    return new ReviewResponse(
	        review.getId(),
	        review.getRating(),
	        review.getComment(),
	        review.getUser().getEmail(),
	        review.getCreatedAt()
	    );
	}

	
	@Transactional
    public List<ReviewResponse> fetchByProduct(Long productId) {
        return productReviewRepo.findByProduct_Id(productId)
            .stream()
            .map(this::toResponse)
            .toList();
    }


	@Transactional
    public List<ProductReview> fetchByUser(Long userId) {
        return productReviewRepo.findByUser_Id(userId);
    }
}
