package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.InvalidIdException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

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
    public CategoryDTO save(SaveCategoryDTO categoryDto) {
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
        validateId(id);

        Category category = categoryRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException("Category does not exist."));

        return categoryMapper.toCategoryDTO( category );
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Long categoryId = category.getId();

        validateId(categoryId);

        if ( ! categoryRepository.existsById( categoryId ) )
            throw new NotFoundException("Category does not exist.");

        return categoryMapper.toCategoryDTO( categoryRepository.save( category ) );
    }

    @Override
    public void delete(Long id) {
        validateId(id);

        if( ! categoryRepository.existsById( id ) )
            throw new NotFoundException("Category does not exist.");

        categoryRepository.deleteById(id);
    }

    private void validateId(Long id) throws InvalidIdException {
        if(id == null)
            throw new InvalidIdException("Id cannot be null.");

        if(id < 1)
            throw new InvalidIdException("Id cannot be less than 1.");
    }
}
