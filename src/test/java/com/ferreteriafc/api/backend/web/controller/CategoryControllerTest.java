package com.ferreteriafc.api.backend.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ferreteriafc.api.backend.domain.service.implementation.CategoryServiceImpl;
import com.ferreteriafc.api.backend.domain.utils.MockObjectFactory;
import com.ferreteriafc.api.backend.domain.dto.CategoryDTO;
import com.ferreteriafc.api.backend.domain.dto.request.SaveCategoryDTO;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryServiceImpl categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private SaveCategoryDTO saveCategoryDTO;
    private CategoryDTO categoryDTO;
    private List<CategoryDTO> categoriesDTO;

    @BeforeEach
    void setUp() {
        saveCategoryDTO = MockObjectFactory.getSaveCategoryDTO();
        categoryDTO = MockObjectFactory.getCategoryDTO();
        categoriesDTO = MockObjectFactory.getCategoryDTOList();
    }

    @Test
    @DisplayName("Method: getAllCategories() - when existing categories should return a list of categoriesDTO with http status code 200 (Ok)")
    void getAllCategories_whenCalledAndCategoriesExist_shouldReturnListOfCategoriesDTOAndStatus200() {
        when(categoryService.findAll()).thenReturn(categoriesDTO);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(categoriesDTO);

        ResponseEntity<?> testResponseEntity = categoryController.getAllCategories();

        assertAll("getAllCategories",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify( categoryService , times(1)).findAll();
    }

    // TODO: Fix test
//    @Test
//    @DisplayName("Method: getCategoryById() - when existing category should return categoryDTO with http status code 200 (Ok)")
//    void getCategoryById_whenCalledAndCategoryExist_shouldReturnCategoryDTOAndStatus200() {
//        when(categoryService.findById(anyInt())).thenReturn(categoryDTO);
//        ResponseEntity<?> expectedResponse = ResponseEntity.ok(categoryDTO);
//
//        ResponseEntity<?> testResponseEntity = categoryController.getCategoryById(1);
//
//        assertAll("getCategoryById",
//            () -> assertEquals(expectedResponse, testResponseEntity),
//            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
//            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
//        );
//        verify( categoryService , times(1)).findById(anyInt());
//    }

    @Test
    @DisplayName("Method: addCategory() - when category doesn't exist should return a new categoryDTO with http status code 201 (Created)")
    void addCategory_whenCalledAndCategoryDoesNotExist_shouldReturnCategoryDTOAndStatus201() {
        when(categoryService.save(any(SaveCategoryDTO.class))).thenReturn(categoryDTO);
        ResponseEntity<?> expectedResponse = ResponseEntity.status(201).body(categoryDTO);

        ResponseEntity<?> testResponseEntity = categoryController.addCategory(saveCategoryDTO);

        assertAll("getCategoryById",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify( categoryService , times(1)).save(any(SaveCategoryDTO.class));
    }

    @Test
    @DisplayName("Method: updateCategory() - when category exist and could be updated should return categoryDTO with http status code 200 (Ok)")
    void updateCategory_whenCalledAndCategoryExist_shouldReturnCategoryDTOAndStatus200() {
        when(categoryService.update(anyInt(), any(CategoryDTO.class))).thenReturn(categoryDTO);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(categoryDTO);

        ResponseEntity<?> testResponseEntity = categoryController.updateCategory(anyInt(), categoryDTO);

        assertAll("getCategoryById",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode()),
            () -> assertEquals(expectedResponse.getBody(), testResponseEntity.getBody())
        );
        verify( categoryService , times(1)).update(anyInt(), any(CategoryDTO.class));
    }

    @Test
    @DisplayName("Method: deleteCategory() - when category exists and could be deleted should return http status code 204 (No content)")
    void deleteCategory_whenCategoryExist_shouldReturnHttpStatus204() {
        ResponseEntity<?> expectedResponse = ResponseEntity.noContent().build();

        ResponseEntity<?> testResponseEntity = categoryController.deleteCategory(1);

        assertAll("getCategoryById",
            () -> assertEquals(expectedResponse, testResponseEntity),
            () -> assertEquals(expectedResponse.getStatusCode(), testResponseEntity.getStatusCode())
        );
        verify( categoryService , times(1)).delete(1);
    }
}
