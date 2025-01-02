package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.dto.request.NewCategoryDTO;

public interface ICategoryService {

    CategoryDTO save(NewCategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO update(CategoryDTO categoryDto);

    void delete(Long id);

}
