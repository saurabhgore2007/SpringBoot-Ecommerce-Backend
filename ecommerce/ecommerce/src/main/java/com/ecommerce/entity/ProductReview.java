package com.ecommerce.entity;

import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productreview")
public class ProductReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private Integer rating;
	
	private String comment;
	@CreationTimestamp
	private OffsetDateTime  createdAt;
	
	@ManyToOne
	@JoinColumn(name = "product_id",nullable=false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable=false)
	private User user;

	public ProductReview() {}
	
	public ProductReview(Integer rating, String comment, Product product,User user) {
		this.rating = rating;
		this.comment = comment;
		this.product = product;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public OffsetDateTime   getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime   createdAt) {
		this.createdAt = createdAt;
	}

	public Product getProduct() {
		return product;
	}

	public void setProducts(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
