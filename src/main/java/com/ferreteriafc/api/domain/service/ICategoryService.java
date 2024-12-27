package com.ferreteriafc.api.domain.service;

import com.ferreteriafc.api.web.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO save(CategoryDTO categoryDto);

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO update(CategoryDTO categoryDto);

    void delete(Long id);

}
