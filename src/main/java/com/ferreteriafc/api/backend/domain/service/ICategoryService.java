package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.domain.dto.CategoryDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveCategoryDTO;
import com.ferreteriafc.api.backend.persistence.entity.Category;

public interface ICategoryService {

    CategoryDTO save(SaveCategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    Category findById(Integer id);

    CategoryDTO toDto(Category category);

    CategoryDTO update(Integer id, CategoryDTO categoryDto);

    void delete(Integer id);

}
