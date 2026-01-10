package com.ecommerce.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Enumerated(EnumType.STRING)
	private OrderStatus status;
    
    private BigDecimal totalAmount;
    
    private OffsetDateTime  createdAt;
    
    @Column(nullable = true)
    private OffsetDateTime  paidAt;

    
    @Column(nullable=true)
    private Long paymentId;
    
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem>  orderItems = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Order() {}
    
    public Order(Long id, OrderStatus status, BigDecimal totalAmount, OffsetDateTime  createdAt, Long paymentId,
    		List<OrderItem> orderItems, User user) {
		this.id = id;
		this.status = status;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.paymentId = paymentId;
		this.orderItems = orderItems;
		this.user = user;
	}
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

//	public OffsetDateTime  getCreatedat() {
//		return createdAt;
//	}
//
//	public void setCreatedat(OffsetDateTime  createdAt) {
//		this.createdAt = createdAt;
//	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public List<OrderItem> getOrder() {
		return orderItems;
	}

	public void setOrder(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OffsetDateTime  getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime  createdAt) {
		this.createdAt = createdAt;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public OffsetDateTime  getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(OffsetDateTime  paidAt) {
		this.paidAt = paidAt;
	}
	
}
