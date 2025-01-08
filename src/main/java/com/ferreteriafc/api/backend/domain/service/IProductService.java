package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;

public interface IProductService {

    ProductDTO save(SaveProductDTO productDTO, MultipartFile file);

    List<ProductDTO> findAll();

    ProductDTO findById(Integer id);

    ProductDTO update(ProductDTO productDTO, MultipartFile file);

    void delete(Integer id);

}
