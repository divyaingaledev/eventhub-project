package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.CategoryRequest;
import com.eventhub.dto.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(
            Long id,
            CategoryRequest request
    );

    void deleteCategory(Long id);
}