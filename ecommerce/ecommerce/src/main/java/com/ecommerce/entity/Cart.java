package com.ecommerce.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal totalAmount;
	private OffsetDateTime  updatedAt;
		
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false) 
	private User user; 
	
	@OneToMany(mappedBy="cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();
	
	public Cart() {
	}
	
	public Cart(Long id, BigDecimal totalAmount, OffsetDateTime  updatedAt) {
		super();
		this.id = id;
		this.totalAmount = totalAmount;
		this.updatedAt = updatedAt;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public OffsetDateTime  getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime  updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public void addItem(CartItem item) {
	    items.add(item);
	    item.setCart(this); 
	}

}
