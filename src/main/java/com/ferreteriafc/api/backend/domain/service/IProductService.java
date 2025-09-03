package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import com.ferreteriafc.api.backend.domain.dto.ProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveProductDTO;
import com.ferreteriafc.api.backend.domain.dto.request.UpdateProductDTO;

public interface IProductService {

    ProductDTO save(SaveProductDTO saveProductDTO);

    List<ProductDTO> findAll();

    ProductDTO findById(Integer id);

    ProductDTO update(Integer id, UpdateProductDTO updateProductDTO);

    void delete(Integer id);

}
