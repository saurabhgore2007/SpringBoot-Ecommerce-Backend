package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.AdminDTO.AdminMetricsResponse;
import com.ecommerce.dto.ProductSummary;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminMetricsService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired ProductRepository productRepository;
	
	@Transactional
	public AdminMetricsResponse getMetrics() {
		
		BigDecimal totalRevenue = orderRepository.totalRevenue();
        List<ProductSummary> topProducts = productRepository.findTopProducts();

        Map<String, Long> ordersByStatus = new LinkedHashMap<>();
        
        for (Object[] row : orderRepository.countOrdersByStatus()) {
            ordersByStatus.put(String.valueOf(row[0]), ((Number) row[1]).longValue());
        }

        return new AdminMetricsResponse(totalRevenue.doubleValue(), topProducts, ordersByStatus);

	}
}
