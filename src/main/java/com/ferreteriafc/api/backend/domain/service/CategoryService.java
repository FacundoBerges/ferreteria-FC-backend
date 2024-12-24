package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDto) {
        System.out.println("CATEGORIES");

        System.out.println("Category DTO: " + categoryDto);

        //Category categoryEntity = categoryMapper.toCategory(categoryDto);
        //Category savedCategory = categoryRepository.save(categoryEntity);

        //System.out.println("Category Entity: " + categoryEntity);

        return categoryDto;
        //return categoryMapper.categoryToCategoryDTO(categoryEntity);
    }

    @Override
    public List<CategoryDTO> findAll() {
        System.out.println("CATEGORIES");
        return List.of();
    }

    @Override
    public CategoryDTO findById(Long id) {
        return null;
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDto) {
        System.out.println(categoryDto);
        return null;
    }

    @Override
    public void delete(CategoryDTO categoryDto) {

    }
}
