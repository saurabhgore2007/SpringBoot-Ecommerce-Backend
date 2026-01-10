package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

	List<Payment>  findByOrder_Id(Long orderId);
	
	boolean existsByOrder_Id(Long id);
}
