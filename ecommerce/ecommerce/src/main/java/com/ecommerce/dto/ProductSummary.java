package com.ecommerce.dto;

import java.math.BigDecimal;

public record ProductSummary(String name, Long soldQuantity, BigDecimal  revenue) {

}
