package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ferreteriafc.api.backend.domain.mapper.IBrandMapper;
import com.ferreteriafc.api.backend.persistence.repository.BrandRepository;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;

@Service
public class BrandServiceImpl implements IBrandService {

    private final BrandRepository brandRepository;
    private final IBrandMapper brandMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, IBrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandDTO> findAll() {
        return List.of();
    }

    @Override
    public BrandDTO findById(Long id) {
        return null;
    }

    @Override
    public BrandDTO save(SaveBrandDTO brandDTO) {
        return null;
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
