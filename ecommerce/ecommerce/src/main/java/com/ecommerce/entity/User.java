package com.ecommerce.entity;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(unique = true, nullable = false)
	@Email
	private String email;
    
    @NotBlank
    @Column(nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    
	private OffsetDateTime  createdAt;

	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();;
	
    @OneToOne(mappedBy="user",cascade = CascadeType.ALL)
    private Cart cart;
    
    public User() {}
    
    public User(Long id, @Email String email, @NotBlank @Size(min = 4, max = 8) String password, UserRole role) {
		super();
		this.id = id;
		this.email = email;
		this.passwordHash = password;
		this.role = role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public OffsetDateTime  getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime  createdAt) {
		this.createdAt = createdAt;
		
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}	

}
