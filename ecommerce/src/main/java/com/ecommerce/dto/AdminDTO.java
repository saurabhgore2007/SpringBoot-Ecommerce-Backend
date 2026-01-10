package com.ecommerce.dto;

import java.util.List;
import java.util.Map;

public class AdminDTO {

	public record AdminMetricsResponse(
		 Double totalRevenue,
	     List<ProductSummary> topProducts,
	     Map<String, Long> ordersByStatus) {}

	//public record ProductSummary(String name,Long soldQuantity,Double revenue) {}

}
