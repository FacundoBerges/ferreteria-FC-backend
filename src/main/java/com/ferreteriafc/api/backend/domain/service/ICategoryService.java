package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;

public interface ICategoryService {

    CategoryDTO save(SaveCategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Integer id);

    CategoryDTO update(Integer id, CategoryDTO categoryDto);

    void delete(Integer id);

}
