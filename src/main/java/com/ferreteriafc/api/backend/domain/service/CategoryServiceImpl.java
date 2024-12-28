package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

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
        Category category = categoryMapper.toCategory(categoryDto);
        String categoryName = category.getName();

        if ( categoryRepository.existsByName( categoryName ) )
            throw new AlreadyExistException("Category already exist.");

        return categoryMapper.toCategoryDTO( categoryRepository.save( category ) );
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();

        if ( categories.isEmpty() )
            throw new NotFoundException("No categories found.");

        return categoryMapper.toCategoryDTOList(categories);
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException("Category does not exist."));

        return categoryMapper.toCategoryDTO( category );
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Long categoryId = category.getId();

        if ( ! categoryRepository.existsById( categoryId ) )
            throw new NotFoundException("Category does not exist.");

        return categoryMapper.toCategoryDTO( categoryRepository.save( category ) );
    }

    @Override
    public void delete(Long id) {
        if( ! categoryRepository.existsById( id ) )
            throw new NotFoundException("Category does not exist.");

        categoryRepository.deleteById(id);
    }

}
