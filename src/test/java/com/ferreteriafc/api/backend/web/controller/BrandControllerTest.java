package com.ferreteriafc.api.backend.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ferreteriafc.api.backend.domain.service.implementation.BrandServiceImpl;
import com.ferreteriafc.api.backend.domain.utils.MockObjectFactory;
import com.ferreteriafc.api.backend.domain.dto.BrandDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveBrandDTO;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

    @Mock
    private BrandServiceImpl brandService;

    @InjectMocks
    private BrandController brandController;

    private SaveBrandDTO saveBrandDTO;
    private BrandDTO brandDTO;
    private List<BrandDTO> brandDTOS;

    @BeforeEach
    void setUp() {
        saveBrandDTO = MockObjectFactory.getSaveBrandDTO();
        brandDTO = MockObjectFactory.getBrandDTO();
        brandDTOS = MockObjectFactory.getBrandDTOList();
    }

    @Test
    @DisplayName("Method: getAllBrands() - when existing brands should return a list of brandsDTO with http status code 200 (Ok)")
    void getAllBrands_whenCalledAndBrandsExist_shouldReturnListOfBrandsDTOAndStatus200() {
        when(brandService.findAll()).thenReturn(brandDTOS);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(brandDTOS);

        ResponseEntity<?> testResponseEntity = brandController.getAllBrands();

        assertAll("getAllBrands",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify(brandService, times(1)).findAll();
    }

    // TODO: Fix test
//    @Test
//    @DisplayName("Method: getBrandById() - when existing brand should return brandDTO with http status code 200 (Ok)")
//    void getBrandById_whenCalledAndBrandExist_shouldReturnBrandDTOAndStatus200() {
//        when(brandService.findById(anyInt())).thenReturn(brandDTO);
//        ResponseEntity<?> expectedResponse = ResponseEntity.ok(brandDTO);
//
//        ResponseEntity<?> testResponseEntity = brandController.getBrandById(1);
//
//        assertAll("getBrandById",
//            () -> assertEquals(expectedResponse, testResponseEntity),
//            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
//            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
//        );
//        verify(brandService, times(1)).findById(anyInt());
//    }

    @Test
    @DisplayName("Method: addBrand() - when brand doesn't exist should return a new brandDTO with http status code 201 (Created)")
    void addBrand_whenCalledAndBrandDoesNotExist_shouldReturnBrandDTOAndStatus201() {
        when(brandService.save(any(SaveBrandDTO.class))).thenReturn(brandDTO);
        ResponseEntity<?> expectedResponse = ResponseEntity.status(201).body(brandDTO);

        ResponseEntity<?> testResponseEntity = brandController.addBrand(saveBrandDTO);

        assertAll("addBrand",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify(brandService, times(1)).save(any(SaveBrandDTO.class));
    }

    @Test
    @DisplayName("Method: updateBrand() - when brand exist and could be updated should return brandDTO with http status code 200 (Ok)")
    void updateBrand_whenCalledAndBrandExist_shouldReturnBrandDTOAndStatus200() {
        when(brandService.update(anyInt(), any(BrandDTO.class))).thenReturn(brandDTO);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(brandDTO);

        ResponseEntity<?> testResponseEntity = brandController.updateBrand(anyInt(), brandDTO);

        assertAll("updateBrand",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify(brandService, times(1)).update(anyInt(), any(BrandDTO.class));
    }

    @Test
    @DisplayName("Method: deleteBrand() - when brand exists and could be deleted should return http status code 204 (No content)")
    void deleteBrand_whenBrandExist_shouldReturnHttpStatus204() {
        ResponseEntity<?> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<?> testResponseEntity = brandController.deleteBrand(1);

        assertAll("deleteBrand",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode())
        );
        verify(brandService, times(1)).delete(1);
    }
}
