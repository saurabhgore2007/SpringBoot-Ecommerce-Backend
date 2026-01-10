package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import com.ecommerce.entity.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentDTO {

	public record PaymentRequest(@NotBlank String provider,@NotNull BigDecimal amount) {}

	public record PaymentResponse(Long id,String provider,BigDecimal amount,PaymentStatus status,String transactionRef,OffsetDateTime  createdAt) {}
}
