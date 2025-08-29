package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.ferreteriafc.api.backend.domain.service.implementation.BrandServiceImpl;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ferreteriafc.api.backend.domain.mapper.BrandMapper;
import com.ferreteriafc.api.backend.domain.utils.MockObjectFactory;
import com.ferreteriafc.api.backend.persistence.entity.Brand;
import com.ferreteriafc.api.backend.persistence.repository.BrandRepository;
import com.ferreteriafc.api.backend.domain.dto.BrandDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveBrandDTO;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandServiceImpl brandService;

    private Brand brand;
    private BrandDTO brandDTO;
    private SaveBrandDTO saveBrandDTO;
    private List<Brand> brandList;
    private List<BrandDTO> brandDTOList;

    @BeforeEach
    void setUp() {
        brand = MockObjectFactory.getBrandEntity();
        brandDTO = MockObjectFactory.getBrandDTO();
        saveBrandDTO = MockObjectFactory.getSaveBrandDTO();

        brandList = MockObjectFactory.getBrands();
        brandDTOList = MockObjectFactory.getBrandDTOList();
    }

    @Test
    @DisplayName("Method: save() - when is valid brand should return brandDTO")
    void save_whenValidBrand_shouldReturnBrandDTO() {
        when(brandRepository.existsByName(anyString())).thenReturn(false);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toBrand(any(SaveBrandDTO.class))).thenReturn(brand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(brandDTO);

        BrandDTO testBrandDTO = brandService.save(saveBrandDTO);

        assertAll("SavedBrand",
            () -> assertNotNull(testBrandDTO),
            () -> assertEquals(brandDTO, testBrandDTO),
            () -> assertEquals(brandDTO.getBrandId(), testBrandDTO.getBrandId()),
            () -> assertEquals(brandDTO.getBrandName(), testBrandDTO.getBrandName())
        );
    }

    @Test
    @DisplayName("Method: save() - when brand name is already in database should throw AlreadyExistsException")
    void save_whenBrandNameAlreadyExists_shouldThrowAlreadyExistsException() {
        when(brandRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> brandService.save(saveBrandDTO));
        verify(brandRepository, never()).save(any());
    }
}
