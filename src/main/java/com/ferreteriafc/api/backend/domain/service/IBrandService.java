package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;

public interface IBrandService {

    BrandDTO save(SaveBrandDTO brandDTO);

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    BrandDTO update(BrandDTO brandDTO);

    void delete(Long id);

}
