package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;

public interface ICategoryService {

    CategoryDTO save(SaveCategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO update(CategoryDTO categoryDto);

    void delete(Long id);

}
