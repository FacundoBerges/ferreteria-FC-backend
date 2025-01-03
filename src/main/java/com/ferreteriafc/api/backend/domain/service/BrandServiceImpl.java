package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ferreteriafc.api.backend.domain.mapper.IBrandMapper;
import com.ferreteriafc.api.backend.persistence.entity.Brand;
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
        List<Brand> brands = brandRepository.findAll();

        if (brands.isEmpty())
            throw new RuntimeException("Brand not found");

        return brandMapper.toBrandDTOList(brands);
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow( () -> new RuntimeException("Brand does not exist"));

        return brandMapper.toBrandDTO(brand);
    }

    @Override
    public BrandDTO save(SaveBrandDTO brandDTO) {
        String brandName = brandDTO.getBrandName();

        if (brandRepository.existsByName(brandName))
            throw new RuntimeException("Brand already exists");

        Brand brand = brandMapper.toBrand(brandDTO);

        return brandMapper.toBrandDTO(brandRepository.save(brand));
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        Brand brand = brandMapper.toBrand(brandDTO);

        if(! brandRepository.existsById(brand.getId()))
            throw new RuntimeException("Brand not found");

        return brandMapper.toBrandDTO(brandRepository.save(brand));
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

}
