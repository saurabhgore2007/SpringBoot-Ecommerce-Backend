package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartDTO {

	public record CartItemRequest(@NotNull Long productId,@Min(1) int quantity) {}

	public record CartItemResponse(Long id,Long productId,String productName,int quantity,BigDecimal priceAtAdd) {}

	public record CartResponse(Long id,List<CartItemResponse> items,BigDecimal totalAmount,OffsetDateTime  updatedAt) {}
}
