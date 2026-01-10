package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CategoryDTO.CategoryCreateRequest;
import com.ecommerce.dto.CategoryDTO.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional
    public CategoryResponse create(CategoryCreateRequest req) {
		
        if (categoryRepository.findByName(req.name()).isPresent()) {
            throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(req.name());
        category.setSlug(req.slug());
        
        if (req.parentId() != null) {
            Category parent = categoryRepository.findById(req.parentId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentId(parent.getId());
        }
        else {
            category.setParentId(null); 
        }

        categoryRepository.save(category);
        return toResponse(category);
    }

	
    private CategoryResponse toResponse(Category c) {
         return new CategoryResponse(c.getId(), c.getName(), c.getSlug(),c.getParentId() != null ? c.getParentId() : null);
    }

    @Transactional
	public void deleteCategory(Long id) {
		 categoryRepository.deleteById(id);
	}
	
    @Transactional
	public List<CategoryResponse> fetchAll() {
	    return categoryRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Transactional
	public CategoryResponse getById(Long id) {
	    Category category = categoryRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

	    return toResponse(category);
	}
	
	public Category getBySlug(String slug) {
        return categoryRepository.findBySlug(slug).orElseThrow(() -> new ResourceNotFoundException("Slug not found"));
    }
}
