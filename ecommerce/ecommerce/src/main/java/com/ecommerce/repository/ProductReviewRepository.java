package com.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {

	List<ProductReview> findByProduct_Id(Long productId);
	List<ProductReview> findByUser_Id(Long userId);
}
