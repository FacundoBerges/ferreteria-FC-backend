package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;
import com.ferreteriafc.api.backend.domain.mapper.BrandMapper;
import com.ferreteriafc.api.backend.persistence.entity.Brand;
import com.ferreteriafc.api.backend.persistence.repository.BrandRepository;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class BrandServiceImpl implements IBrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDTO save(SaveBrandDTO brandDTO) {
        String brandName = brandDTO.getBrandName();

        if (brandRepository.existsByName(brandName))
            throw new AlreadyExistException("Brand already exists.");

        Brand brand = brandMapper.toBrand(brandDTO);

        return brandMapper.toBrandDTO(brandRepository.save(brand));
    }

    @Override
    public List<BrandDTO> findAll() {
        List<Brand> brands = brandRepository.findAll();

        if (brands.isEmpty())
            throw new NotFoundException("No brands found.");

        return brandMapper.toBrandDTOList(brands);
    }

    @Override
    public BrandDTO findById(Integer id) {
        validateId(id);

        Brand brand = brandRepository
                        .findById(id)
                        .orElseThrow( () -> new NotFoundException("Brand does not exist."));

        return brandMapper.toBrandDTO(brand);
    }

    @Override
    public BrandDTO update(Integer id, BrandDTO brandDTO) {
        validateId(id);

        Brand brand = brandMapper.toBrand(brandDTO);

        if( ! brandRepository.existsById(id) )
            throw new NotFoundException("Brand does not exist.");

        return brandMapper.toBrandDTO(brandRepository.save(brand));
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        if ( ! brandRepository.existsById(id))
            throw new NotFoundException("Brand does not exist.");

        brandRepository.deleteById(id);
    }

}
