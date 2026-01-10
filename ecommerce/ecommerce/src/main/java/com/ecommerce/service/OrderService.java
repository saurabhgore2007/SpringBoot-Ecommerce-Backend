package com.ecommerce.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderDTO.OneOrderResponse;
import com.ecommerce.dto.OrderDTO.OrderCreateRequest;
import com.ecommerce.dto.OrderDTO.OrderItemResponse;
import com.ecommerce.dto.OrderDTO.OrderResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;


@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
    private static final ZoneId INDIA = ZoneId.of("Asia/Kolkata");
	
	@Transactional
	public OrderResponse createOrder(User user , OrderCreateRequest req) {
		
		Cart cart = cartRepository.findById(req.cartId()).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		
		if (!cart.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Cart does not belong to user");
        }
		
		if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(OffsetDateTime.now(INDIA));
        order.setStatus(OrderStatus.CREATED);
        	
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for(CartItem cartItem : cart.getItems()) {
        	
        	Product product = cartItem.getProduct();
        	int qty = cartItem.getQuantity();
        	
        	if (product.getStock() < qty) {
                throw new ResourceNotFoundException("Insufficient stock for product: " + product.getName());
            }
            
        	product.setStock(product.getStock() - qty);
        	
        	OrderItem item = new OrderItem();
            item.setProducts(product);
            item.setQuantity(qty);
            item.setUnitPrice(cartItem.getPriceAtAdd());
            item.setOrder(order);

            orderItems.add(item);
            total = total.add(cartItem.getPriceAtAdd().multiply(BigDecimal.valueOf(qty)));

        }
        
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        cart.getItems().clear();
        cart.setUpdatedAt(OffsetDateTime.now(INDIA));
        
        orderRepository.save(order);
        cartRepository.save(cart);

		
		List<OrderItemResponse> items = order.getOrderItems().stream()
		           .map(item -> new OrderItemResponse(
		                        item.getId(),
		                        item.getProducts().getId(),
		                        item.getProducts().getName(),
		                        item.getQuantity(),
		                        item.getUnitPrice()
		                ))
		                .toList();
		
		return new OrderResponse(
	                order.getId(),
	                order.getStatus(),
	                order.getTotalAmount(),
	                order.getCreatedAt(),
	                items
	            );
	}
	 
	@Transactional
      public List<OrderResponse> listOrders(User user) {
  		
    	  List<Order> orders = orderRepository.findByUser_Id(user.getId());
    	
    	if(orders == null) {
            throw new ResourceNotFoundException("Order Not Found!");
    	}
  		
    	return orders.stream()
    	        .map(order -> new OrderResponse(
    	            order.getId(),
    	            order.getStatus(),
    	            order.getTotalAmount(),
    	            order.getCreatedAt(),
    	            order.getOrderItems().stream()
    	                .map(item -> new OrderItemResponse(
    	                    item.getId(),
    	                    item.getProducts().getId(),
    	                    item.getProducts().getName(),
    	                    item.getQuantity(),
    	                    item.getUnitPrice()
    	                ))
    	                .toList()
    	        ))
    	        .toList();


  	}
      
	@Transactional
      public OneOrderResponse getOrderById(User user,Long orderId) {
    		
    	  Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id : " + orderId));

    	  if(!order.getUser().getId().equals(user.getId())) {
    		  throw new ResourceNotFoundException("Order does not belong to this user!");

    	  }
    	   
    		 return new OneOrderResponse(
		                order.getId(),
		                order.getStatus(),
		                order.getTotalAmount(),
		                order.getCreatedAt()
		            );
    	}
      
	@Transactional
      public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
    	    Order order = orderRepository.findById(orderId)
    	        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    	    OrderStatus current = order.getStatus();

    	    if (!isValidTransition(current, newStatus)) {
    	        throw new ResourceNotFoundException("Invalid status transition: " + current + " → " + newStatus);
    	    }

    	    order.setStatus(newStatus);
    	    orderRepository.save(order);
    	}

      private boolean isValidTransition(OrderStatus current, OrderStatus target) {
    	    return switch (current) {
    	        case CREATED -> target == OrderStatus.PAID || target == OrderStatus.CANCELLED;
    	        case PAID    -> target == OrderStatus.SHIPPED || target == OrderStatus.CANCELLED;
    	        case SHIPPED, CANCELLED -> false;
    	    };
    	}
      
      @Transactional
      public BigDecimal fetchTotalRevenue() {
          BigDecimal revenue = orderRepository.totalRevenue();
          return revenue != null ? revenue : BigDecimal.ZERO; 
      }
}
