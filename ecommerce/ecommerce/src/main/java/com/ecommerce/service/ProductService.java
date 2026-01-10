package com.ecommerce.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDTO.ProductCreateRequest;
import com.ecommerce.dto.ProductDTO.ProductResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private  ProductRepository productRepo;
	@Autowired
	private  CategoryRepository cartRepo;
	
    private static final ZoneId INDIA = ZoneId.of("Asia/Kolkata");
 
//	 public ProductService(ProductRepository repo, CategoryRepository catRepo) 
//	 { 
//		 this.productRepo = repo; 
//		 this.catRepo = catRepo; 
//	 } 
	 
	  @Transactional 
	  public ProductResponse create(ProductCreateRequest req) { 
		  
	    if (productRepo.findBySku(req.sku()).isPresent()) throw new BadRequestException("SKU already exists");
	    
	    Category  category = cartRepo.findById(req.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	    
	    Product product = new Product(); 
	    
	    product.setName(req.name()); 
	    product.setSku(req.sku());
	    product.setPrice(req.price()); 
	    product.setStock(req.stock());
	    product.setDescription(req.description());
	    product.setIsActive(true); 
	    product.setCreatedAt(OffsetDateTime.now(INDIA));
	    product.setUpdatedAt(OffsetDateTime.now(INDIA));

	    category.addProduct(product);

	    Product saved = productRepo.save(product);
	    return toResponse(saved);
  } 
	
	  @Transactional
	  public List<ProductResponse> listByFilter(String categoryName, BigDecimal min, BigDecimal max) {
		    BigDecimal minVal = (min != null) ? min : BigDecimal.ZERO;
		    BigDecimal maxVal = (max != null) ? max : new BigDecimal("100000000");

		    List<Product> products;

		    if (categoryName != null && !categoryName.isEmpty()) {
		        if (min != null || max != null) {
		            products = productRepo.findByCategoryAndPriceRange(categoryName, minVal, maxVal);
		        } 
		        else {
		            products = productRepo.findByCategory_NameAndIsActiveTrue(categoryName);
		        }
		    } else {
		        
		        products = productRepo.findActiveInPriceRange(minVal, maxVal);
		    }

		    return products.stream().map(this::toResponse).toList();
		}
	 
	  private ProductResponse toResponse(Product p) {
		    return new ProductResponse(
		        p.getId(),
		        p.getName(),
		        p.getSku(),
		        p.getCategory() != null ? p.getCategory().getId() : null,
		        p.getCategory() != null ? p.getCategory().getName() : null,
		        p.getPrice(),
		        p.getStock(),
		        p.getIsActive(),
		        p.getDescription()
		    );
		}

	
      public Product getById(Long id) {
      return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
      }
      
      @Transactional
      public ProductResponse updateProduct(Long id, ProductCreateRequest request) {
    	  
    	    Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    	    cartRepo.findById(request.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    	    
    	    
    	    product.setName(request.name()); 
    	    product.setSku(request.sku());
    	    product.setPrice(request.price()); 
    	    product.setStock(request.stock());
    	    product.setDescription(request.description());
    	    product.setIsActive(true); 
    	    product.setUpdatedAt(OffsetDateTime.now(INDIA));

    	    productRepo.save(product);
    	    
    	    return new ProductResponse(product.getId(),product.getName(),product.getSku(),
    	    		product.getCategory() != null ? product.getCategory().getId() : null,
    	    		product.getCategory() != null ? product.getCategory().getName() : null,
    	    		product.getPrice(),product.getStock(),product.getIsActive(),product.getDescription()
    	    );
    	}

      @Transactional
      public void deleteProduct(Long id) {
    	  
  	    productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found to Delete"));
    	productRepo.deleteById(id);
      }
 
}
	

