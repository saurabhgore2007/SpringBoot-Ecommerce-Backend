package com.ecommerce.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.PaymentDTO.PaymentRequest;
import com.ecommerce.dto.PaymentDTO.PaymentResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.PaymentRepository;
import com.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    private static final ZoneId INDIA = ZoneId.of("Asia/Kolkata");
	
    @Transactional
	public PaymentResponse initiatePayment(String email,Long id,PaymentRequest req) {
		
		Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		
		if (paymentRepository.existsByOrder_Id(id)) {
		    throw new BadRequestException("Payment already exists for this order");
		}

		
		if(order.getStatus()!= OrderStatus.CREATED) {
			throw new BadRequestException("Only CREATED orders can be paid");
		}
		
		if (!order.getTotalAmount().equals(req.amount())) {
	        throw new BadRequestException("Payment amount must equal order total amount");
	    }

		
		Payment payment = new Payment();
		payment.setProvider(req.provider());
		payment.setAmount(req.amount());
		payment.setStatus(PaymentStatus.SUCCESS);
		payment.setTransactionRef(UUID.randomUUID().toString());
		payment.setCreatedAt(OffsetDateTime.now(INDIA));
		payment.setOrder(order);
		payment.setUser(user);
		
		Payment saved = paymentRepository.save(payment);
		
		order.setPaymentId(saved.getId());
		order.setStatus(OrderStatus.PAID);
		order.setPaidAt(OffsetDateTime.now(INDIA));
		
		orderRepository.save(order);
		
         
         return new PaymentResponse(saved.getId(),saved.getProvider(),saved.getAmount(),saved.getStatus(),saved.getTransactionRef(),saved.getCreatedAt());
    }
	
	
    @Transactional
	public List<PaymentResponse> fetchPaymentsByOrder(Long orderId) {
		
		return paymentRepository.findByOrder_Id(orderId).stream()
	            .map(payment -> new PaymentResponse(
	                payment.getId(),
	                payment.getProvider(),
	                payment.getAmount(),
	                payment.getStatus(),
	                payment.getTransactionRef(),
	                payment.getCreatedAt()
	            ))
	            .toList();
    }
}
