package com.ecommerce.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "products")
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false,unique = true)
	private String sku;
	
	private String description;
	
	@Column(nullable=false)
	private BigDecimal price;
	
	@Column(nullable=false)
	
	private Integer stock;
	private boolean isActive;
	private OffsetDateTime  createdAt;
	private OffsetDateTime  updatedAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id", nullable = false)
	@JsonBackReference
	private Category category;
	
	@OneToMany(mappedBy="product", cascade = CascadeType.ALL)
	private List<ProductReview> reviews;

	@OneToMany(mappedBy = "product")
    private List<OrderItem> orderItem;

	
	//  many to many
	
	public Product() {}
	
	public Product(long id, String name, String sku, String description, BigDecimal price, Integer stock,
			boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt, Category category,
			List<ProductReview> reviews, List<OrderItem> orderItem) {
		super();
		this.id = id;
		this.name = name;
		this.sku = sku;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.category = category;
		this.reviews = reviews;
		this.orderItem = orderItem;
	}





	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public OffsetDateTime  getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime  createdAt) {
		this.createdAt = createdAt;
	}
	public OffsetDateTime  getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(OffsetDateTime  updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		 this.category = category;
	}

	public void setIsActive(boolean b) {
		this.isActive = b;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}
	
}
