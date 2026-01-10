package com.ecommerce.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartDTO.CartItemResponse;
import com.ecommerce.dto.CartDTO.CartResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    private static final ZoneId INDIA = ZoneId.of("Asia/Kolkata");

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser_Id(user.getId()).orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItems(new ArrayList<>());
                    return cartRepository.save(cart);
                });
    }


    @Transactional
    public CartResponse getCartByUserEmail(String email) {
    	
    	User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    	
    	Cart cart = getOrCreateCart(user);
    	
    	recalcTotals(cart);

    	List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtAdd() 
                )).toList();

        return new CartResponse(cart.getId(),items,cart.getTotalAmount(),cart.getUpdatedAt());

    }

    
    @Transactional 
    public CartResponse  addItem(User user, Long productId, int qty) {
    	
      Cart cart = getOrCreateCart(user); 
      Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
      
      if (product.getStock() < qty) throw new BadRequestException("Insufficient Stock"); 
      
      product.setStock(product.getStock() - qty);
      productRepository.save(product);

      
          CartItem cartItem = new CartItem();
          cartItem.setProduct(product);
          cartItem.setQuantity(qty);
          cartItem.setPriceAtAdd(product.getPrice());
          cart.addItem(cartItem);

      recalcTotals(cart);
      cart.setUpdatedAt(OffsetDateTime.now(INDIA));
      cartRepository.save(cart);
      
      return new CartResponse(
              cart.getId(),
              cart.getItems().stream()
                      .map(item -> new CartItemResponse(
                              item.getId(),
                              item.getProduct().getId(),
                              item.getProduct().getName(),
                              item.getQuantity(),
                              item.getPriceAtAdd()
                      ))
                      .toList(),
              cart.getTotalAmount(),
              cart.getUpdatedAt()
      );

    } 
   
    @Transactional
    public CartResponse  updateQty(User user,Long itemId, Integer qty) {
    	
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Item does not belong to user's cart");
        }

        if (item.getProduct().getStock() < qty) {
            throw new BadRequestException("Insufficient stock");
        }

        Product product = item.getProduct();
        int oldQty = item.getQuantity();

        
        int diff = qty - oldQty;

        if (diff > 0) { 
            
            if (product.getStock() < diff) {
                throw new BadRequestException("Insufficient stock");
            }
            product.setStock(product.getStock() - diff);
        } 
        else if (diff < 0) {
            
            product.setStock(product.getStock() + Math.abs(diff));
        }

        productRepository.save(product);

        
        item.setQuantity(qty);
        cartItemRepository.save(item);

        Cart cart = item.getCart();
        recalcTotals(cart);
        cart.setUpdatedAt(OffsetDateTime.now(INDIA));
        cartRepository.save(cart);
        
        return new CartResponse(
                cart.getId(),
                cart.getItems().stream()
                        .map(Item -> new CartItemResponse(
                        		Item.getId(),
                        		Item.getProduct().getId(),
                        		Item.getProduct().getName(),
                        		Item.getQuantity(),
                        		Item.getPriceAtAdd()
                        ))
                        .toList(),
                cart.getTotalAmount(),
                cart.getUpdatedAt()
        );
    }

    @Transactional
    public CartResponse removeItem(User user,Long itemId) {
    	
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found To Delete"));
        Cart cart = item.getCart();
        
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Item does not belong to user's cart to delete");
        }
        
        Product product = item.getProduct();

        product.setStock(product.getStock() + item.getQuantity());
        productRepository.save(product);

        
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        recalcTotals(cart);
        cart.setUpdatedAt(OffsetDateTime.now(INDIA));
        cartRepository.save(cart);
        
        return new CartResponse(
                cart.getId(),
                cart.getItems().stream()
                        .map(i -> new CartItemResponse(
                                i.getId(),
                                i.getProduct().getId(),
                                i.getProduct().getName(),
                                i.getQuantity(),
                                i.getPriceAtAdd()
                        ))
                        .toList(),
                cart.getTotalAmount(),
                cart.getUpdatedAt()
        );
    }
    
    @Transactional
    public CartResponse clearCart(User user) {
    	Cart cart = getOrCreateCart(user);

    	for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

    	
    	cartItemRepository.deleteAll(cart.getItems());
    	cart.getItems().clear();

    	recalcTotals(cart);
    	cart.setUpdatedAt(OffsetDateTime.now(INDIA));
        cartRepository.save(cart);
    	
    	return new CartResponse(
                cart.getId(),
                List.of(),
                cart.getTotalAmount(),
                cart.getUpdatedAt()
        );

    }
    
    private void recalcTotals(Cart cart) { 
        var total = cart.getItems().stream().map(i -> i.getPriceAtAdd().multiply(BigDecimal.valueOf(i.getQuantity()))) 
          .reduce(BigDecimal.ZERO, BigDecimal::add); 
     
        cart.setTotalAmount(total); 
      } 
        
}
