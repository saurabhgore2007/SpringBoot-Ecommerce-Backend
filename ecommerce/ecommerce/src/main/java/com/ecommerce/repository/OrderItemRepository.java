package com.ecommerce.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
	
	boolean existsById(Long id);
	Optional<OrderItem> findByProductId(Long id);

}
