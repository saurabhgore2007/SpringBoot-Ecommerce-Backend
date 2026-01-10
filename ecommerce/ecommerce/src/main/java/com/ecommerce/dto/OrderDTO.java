package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import com.ecommerce.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {

	public record OrderCreateRequest(Long cartId) {}

	public record OrderItemResponse(Long id,Long productId,String productName,int quantity,BigDecimal unitPrice) {}

	public record OrderResponse(Long id,OrderStatus status,BigDecimal totalAmount,OffsetDateTime  createdAt,List<OrderItemResponse> items) {}
	
	public record OneOrderResponse(Long id,OrderStatus status,BigDecimal totalAmount,OffsetDateTime  createdAt) {}

	public record OrderStatusUpdateRequest(@NotNull OrderStatus status) {}

}
