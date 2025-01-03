package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;

public interface IBrandService {

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    BrandDTO save(SaveBrandDTO brandDTO);

    BrandDTO update(BrandDTO brandDTO);

    void delete(Long id);

}
