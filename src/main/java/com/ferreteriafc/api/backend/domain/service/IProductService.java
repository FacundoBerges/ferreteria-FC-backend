package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.domain.dto.ProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveProductDTO;

public interface IProductService {

    ProductDTO save(SaveProductDTO saveProductDTO);

    List<ProductDTO> findAll();

    ProductDTO findById(Integer id);

    ProductDTO update(Integer id, ProductDTO productDTO);

    void delete(Integer id);

}
