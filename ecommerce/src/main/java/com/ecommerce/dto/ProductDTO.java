package com.ecommerce.dto;

import java.math.BigDecimal;

public class ProductDTO {

	public record ProductCreateRequest(
		    String name,
		    String sku,
		    Long categoryId,       
		    BigDecimal price,
		    Integer stock,
		    Boolean active,        
		    String description     
		) {}
	public record ProductResponse(
		    Long id,
		    String name,
		    String sku,
		    Long categoryId,       
		    String categoryName,  
		    BigDecimal price,
		    Integer stock,
		    Boolean active,
		    String description
		) {}
}

