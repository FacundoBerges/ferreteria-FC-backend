package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO save(CategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO update(CategoryDTO categoryDto);

    void delete(CategoryDTO categoryDto);

}
