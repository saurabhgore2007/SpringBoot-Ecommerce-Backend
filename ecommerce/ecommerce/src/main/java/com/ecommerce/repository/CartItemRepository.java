package com.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId); 
} 