package com.eventhub.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.CategoryRequest;
import com.eventhub.dto.CategoryResponse;
import com.eventhub.entity.Category;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.mapper.CategoryMapper;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"));

        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(
            Long id,
            CategoryRequest request) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"));

        categoryMapper.updateEntity(category, request);

        Category updatedCategory =
                categoryRepository.save(category);

        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"));

        categoryRepository.delete(category);
    }
}