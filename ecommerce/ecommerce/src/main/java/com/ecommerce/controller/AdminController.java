package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.AdminDTO.AdminMetricsResponse;
import com.ecommerce.service.AdminMetricsService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminMetricsService MetricService;
	
	@GetMapping("/metrics")
	@PreAuthorize("hasRole('ADMIN')")
	public AdminMetricsResponse getMetrics() {
        return MetricService.getMetrics();
    }
}
