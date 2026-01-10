package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.OrderItem;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.OrderItemRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Transactional
	public OrderItem saveOrder(OrderItem orderItem) {
		return orderItemRepo.save(orderItem);
	}
	
	@Transactional
	public OrderItem updateOrder(OrderItem orderItem) {
		
		if(!orderItemRepo.existsById(orderItem.getId())) {
            throw new ResourceNotFoundException("Order Not Found!");
		}
		
		return orderItemRepo.save(orderItem);
	}
	
	@Transactional
    public void deleteOrder(Long id) {
		
		if(!orderItemRepo.existsById(id)) {
            throw new ResourceNotFoundException("Order Not Found to delete");
		}
		
		orderItemRepo.deleteById(id);
	}
      
	@Transactional
      public List<OrderItem> fetchAll() {
  		
  		if(orderItemRepo.count()==0) {
              throw new ResourceNotFoundException("Order Not Found!");
  		}
  		
  		return orderItemRepo.findAll();
  	}
      
	@Transactional
      public OrderItem fetchById(Long Id) {
    		
    		return orderItemRepo.findByProductId(Id).orElse(null);
    	}
      
}
