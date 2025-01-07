package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;

public interface IProductService {

    ProductDTO save(SaveProductDTO productDTO);

    List<ProductDTO> findAll();

    ProductDTO findById(Integer id);

    ProductDTO update(ProductDTO productDTO);

    void delete(Integer id);

}
