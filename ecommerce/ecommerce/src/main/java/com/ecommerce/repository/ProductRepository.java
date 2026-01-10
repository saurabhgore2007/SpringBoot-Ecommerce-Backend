package com.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.dto.ProductSummary;
import com.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { 

	Optional<Product> findBySku(String sku); 

	List<Product> findByCategory_NameAndIsActiveTrue(String categoryName); 

	@Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price BETWEEN :min AND :max AND p.isActive = true")
    List<Product> findByCategoryAndPriceRange(@Param("categoryName") String categoryName,
                                              @Param("min") BigDecimal min,
                                              @Param("max") BigDecimal max);
	
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max AND p.isActive = true") 
	List<Product> findActiveInPriceRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
	
	@Query("SELECT new com.ecommerce.dto.ProductSummary(" +
		       "p.name, " +
		       "SUM(oi.quantity), " +
		       "SUM(oi.unitPrice * oi.quantity)) " +
		       "FROM OrderItem oi " +
		       "JOIN oi.product p " +
		       "GROUP BY p.name " +
		       "ORDER BY SUM(oi.quantity) ASC")
		List<ProductSummary> findTopProducts();

}
