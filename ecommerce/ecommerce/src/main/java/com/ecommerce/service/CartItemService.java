package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.CartItem;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Transactional
	public CartItem saveItem(CartItem item) {
		return cartItemRepository.save(item);
	}
	
	@Transactional
	public CartItem updateItem(CartItem item) {
		
		if(!cartItemRepository.existsById(item.getId())) {
			throw new ResourceNotFoundException("Item Not Found to Update!");
		}
		return cartItemRepository.save(item);
	}
	
	@Transactional
	public void delete(Long id) {
		 cartItemRepository.deleteById(id);
	}
	
	@Transactional
	public List<CartItem> fetchAll() {
		 return cartItemRepository.findAll();
	}
	
	@Transactional
	public CartItem fetchByCartAndProduct(Long cartId, Long productId) {
        return cartItemRepository.findByCart_IdAndProduct_Id(cartId, productId).orElse(null);
    }
}
