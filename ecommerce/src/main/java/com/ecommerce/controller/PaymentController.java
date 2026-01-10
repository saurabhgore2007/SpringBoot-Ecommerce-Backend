package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.PaymentDTO.PaymentRequest;
import com.ecommerce.dto.PaymentDTO.PaymentResponse;
import com.ecommerce.service.PaymentService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/orders/{id}/payments")
public class PaymentController {
	
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<PaymentResponse> initiatePayment(Authentication auth,@PathVariable Long id,@RequestBody @Valid PaymentRequest req){
		
		String email = auth.getName();
		
		return ResponseEntity.ok(paymentService.initiatePayment(email,id,req));
	}
	
	@GetMapping
	public ResponseEntity<List<PaymentResponse>> getPaymentStatus(@PathVariable Long id){
		
		
		return ResponseEntity.ok(paymentService.fetchPaymentsByOrder(id));
	}

}
