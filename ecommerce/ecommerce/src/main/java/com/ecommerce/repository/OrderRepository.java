package com.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> { 

	boolean existsById(Long Id);
	
	List<Order> findByUser_Id(Long userId);
	
	Optional<Order> findById(Long orderId);

    @Query("SELECT COALESCE(SUM(o.totalAmount),0) FROM Order o WHERE o.status = 'PAID'") 
    BigDecimal totalRevenue();
    
    @Query("SELECT o.status, COUNT(o) FROM Order o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();

} 