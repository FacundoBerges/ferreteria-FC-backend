package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.domain.dto.BrandDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveBrandDTO;

public interface IBrandService {

    BrandDTO save(SaveBrandDTO brandDTO);

    List<BrandDTO> findAll();

    BrandDTO findById(Integer id);

    BrandDTO update(Integer id, BrandDTO brandDTO);

    void delete(Integer id);

}
