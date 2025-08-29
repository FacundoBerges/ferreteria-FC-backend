package com.ferreteriafc.api.backend.domain.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;
import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.domain.service.ICategoryService;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.domain.dto.request.SaveCategoryDTO;
import com.ferreteriafc.api.backend.domain.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.EmptyListException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class CategoryServiceImpl implements ICategoryService {

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
            throw new EmptyListException("No categories found.");

        return categoryMapper.toCategoryDTOList(categories);
    }

    @Override
    public CategoryDTO findById(Integer id) {
        validateId(id);

        Category category = categoryRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException("Category does not exist."));

        return categoryMapper.toCategoryDTO( category );
    }

    @Override
    public CategoryDTO update(Integer id, CategoryDTO categoryDto) {
        validateId(id);

        Category category = categoryMapper.toCategory(categoryDto);

        if ( ! categoryRepository.existsById( id ) )
            throw new NotFoundException("Category does not exist.");

        return categoryMapper.toCategoryDTO( categoryRepository.save( category ) );
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        if( ! categoryRepository.existsById( id ) )
            throw new NotFoundException("Category does not exist.");

        categoryRepository.deleteById(id);
    }

}
