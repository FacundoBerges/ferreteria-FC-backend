package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.domain.dto.BrandDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveBrandDTO;
import com.ferreteriafc.api.backend.persistence.entity.Brand;

public interface IBrandService {

    BrandDTO save(SaveBrandDTO brandDTO);

    List<BrandDTO> findAll();

    Brand findById(Integer id);

    BrandDTO toDto(Brand brand);

    BrandDTO update(Integer id, BrandDTO brandDTO);

    void delete(Integer id);

}
