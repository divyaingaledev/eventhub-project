package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.CategoryRequest;
import com.eventhub.dto.CategoryResponse;
import com.eventhub.entity.Category;

@Component
public class CategoryMapper {

    // Convert DTO to Entity
    public Category toEntity(CategoryRequest request) {

        Category category = new Category();

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return category;
    }

    // Convert Entity to Response DTO
    public CategoryResponse toResponse(Category category) {

        if (category == null) {
            return null;
        }

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        return response;
    }

    // Update existing Entity
    public void updateEntity(
            Category category,
            CategoryRequest request) {

        category.setName(request.getName());
        category.setDescription(request.getDescription());
    }
}