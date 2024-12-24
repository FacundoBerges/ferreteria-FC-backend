package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDto) {
        return categoryMapper.toCategoryDTO(
            categoryRepository.save(
                categoryMapper.toCategory(categoryDto)
            )
        );
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryMapper.toCategoryDTOList( categoryRepository.findAll() );
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Category doesn't exists")
        );

        return categoryMapper.toCategoryDTO( category );
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDto) {
        System.out.println(categoryDto);
        return null;
    }

    @Override
    public void delete(CategoryDTO categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);

        if( ! this.existsById(category.getId()) ) {
            throw new RuntimeException("Category does not exist");
        }

        categoryRepository.delete(category);
    }

    private boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
