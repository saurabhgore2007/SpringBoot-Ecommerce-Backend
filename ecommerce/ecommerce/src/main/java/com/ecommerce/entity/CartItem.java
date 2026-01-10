package com.ecommerce.entity;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantity;
	private BigDecimal  priceAtAdd;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	public CartItem() {}
	
	public CartItem(Long id, Integer quantity, BigDecimal priceAtAdd) {
		this.id = id;
	    this.quantity = quantity;
		this.priceAtAdd = priceAtAdd;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPriceAtAdd() {
		return priceAtAdd;
	}
	public void setPriceAtAdd(BigDecimal priceAtAdd) {
		this.priceAtAdd = priceAtAdd;
	}


	public Cart getCart() {
		return cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
