package com.ecommerce.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String name;
	
	private String slug;
	
	@Column(nullable=true)
	private Long parentId;
	
	@OneToMany(mappedBy="category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Product> products = new ArrayList<>();

	public Category() {}
	
	public Category(long id, String name, String slug, Long parentId, List<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.parentId = parentId;
		this.products = products;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

}
