package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

	public record CategoryCreateRequest(@NotBlank String name,String slug,Long parentId) {}

	public record CategoryResponse(Long id,String name,String slug,Long parentId) {}

}
