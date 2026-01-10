package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

	boolean existsByName(String name);
	Optional<Category> findBySlug(String slug);
	Optional<Category> findByName(String name);
    List<Category> findByParentId(Long parentId);

}
