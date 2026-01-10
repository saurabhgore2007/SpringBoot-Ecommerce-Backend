package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO.ProductCreateRequest;
import com.ecommerce.dto.ProductDTO.ProductResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/products") 
public class ProductsController { 
	
  @Autowired	
  private final ProductService productService;
   
  public ProductsController(ProductService productService) 
  { 
	  this.productService = productService; 
  } 

  @PostMapping 
  @PreAuthorize("hasRole('ADMIN')") 
  public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest req) { 
     
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(req)); 
  } 
 
  @GetMapping 
  public ResponseEntity<List<ProductResponse>> listAll( 
      @RequestParam(required=false) String category, 
      @RequestParam(required=false) BigDecimal minPrice, 
      @RequestParam(required=false) BigDecimal maxPrice) { 
    return ResponseEntity.ok(productService.listByFilter(category, minPrice, maxPrice)); 
  } 
 
  @GetMapping("/{id}") 
  public ResponseEntity<Product> getById(@PathVariable Long id) { 
      return ResponseEntity.ok(productService.getById(id));

  }
  
  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')") 
  public ResponseEntity<ProductResponse> updateById(@PathVariable Long id,@Valid @RequestBody ProductCreateRequest req) { 
	  
	  ProductResponse updated = productService.updateProduct(id, req);
	  return ResponseEntity.ok(updated);
  }
  
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')") 
  public ResponseEntity<Product> deleteById(@PathVariable Long id) { 
	  productService.deleteProduct(id);
	    return ResponseEntity.noContent().build();
  }
  
  
  
} 
