package com.ferreteriafc.api.backend.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ferreteriafc.api.backend.domain.utils.MockObjectFactory;
import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import com.ferreteriafc.api.backend.persistence.entity.Category;
import com.ferreteriafc.api.backend.persistence.repository.CategoryRepository;
import com.ferreteriafc.api.backend.web.dto.request.SaveCategoryDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;
import com.ferreteriafc.api.backend.web.exception.InvalidIdException;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDTO categoryDTO;
    private SaveCategoryDTO saveCategoryDTO;
    private List<Category> categories;
    private List<CategoryDTO> categoriesDTOs;

    @BeforeEach
    void setUp() {
        category = MockObjectFactory.getCategoryEntity();
        categoryDTO = MockObjectFactory.getCategoryDTO();
        saveCategoryDTO = MockObjectFactory.getSaveCategoryDTO();

        categories = MockObjectFactory.getCategories();
        categoriesDTOs = MockObjectFactory.getCategoryDTOList();
    }


    @Test
    void findAll_whenExistingCategories_thenReturnCategoriesDTOs() {
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toCategoryDTOList(anyList())).thenReturn(categoriesDTOs);

        List<CategoryDTO> testCategoriesDTO = categoryService.findAll();

        assertAll("Categories",
            () -> assertNotNull(testCategoriesDTO),

            () -> assertEquals(2, testCategoriesDTO.size()),

            () -> assertEquals(categoryDTO, testCategoriesDTO.get(0)),

            () -> assertEquals(1L, testCategoriesDTO.get(0).getCategoryId().longValue()),
            () -> assertEquals("Category 1", testCategoriesDTO.get(0).getCategoryName()),
            () -> assertEquals("Image URL 1", testCategoriesDTO.get(0).getCategoryImage()),

            () -> assertEquals(2L, testCategoriesDTO.get(1).getCategoryId().longValue()),
            () -> assertEquals("Category 2", testCategoriesDTO.get(1).getCategoryName()),
            () -> assertEquals("Image URL 2", testCategoriesDTO.get(1).getCategoryImage())
        );
    }

    @Test
    void findAll_whenNoCategories_shouldThrowNotFoundException() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, categoryService::findAll);
    }


    @Test
    void findById_whenValidIdAndExistingEntity_shouldReturnCategoryDTO() {
        Long id = 1L;
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO testCategoryDTO = categoryService.findById(id);

        assertAll("Category",
            () -> assertNotNull(testCategoryDTO),

            () -> assertEquals(category.getId(), testCategoryDTO.getCategoryId()),
            () -> assertEquals(category.getName(), testCategoryDTO.getCategoryName()),
            () -> assertEquals(category.getImageUrl(), testCategoryDTO.getCategoryImage()),
            () -> assertEquals(categoryDTO, testCategoryDTO)
        );
    }

    @Test
    void findById_whenValidIdAndNoExistingEntity_shouldThrowNotFoundException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.findById(1L));
    }

    @Test
    void findById_whenIdIsNull_shouldThrowInvalidIdException() {
        assertThrows(InvalidIdException.class, () -> categoryService.findById(null));
    }

    @Test
    void findById_whenIdIsNegativeOrZero_shouldThrowInvalidIdException() {
        assertThrows(InvalidIdException.class, () -> categoryService.findById(0L));
        assertThrows(InvalidIdException.class, () -> categoryService.findById(-1L));
    }


    @Test
    void save_whenValidCategory_shouldReturnCategoryDTO() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategory(any(SaveCategoryDTO.class))).thenReturn(category);
        when(categoryMapper.toCategoryDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO testCategoryDTO = categoryService.save(saveCategoryDTO);

        assertAll("SavedCategory",
            () -> assertNotNull(testCategoryDTO),

            () -> assertEquals(category.getId(), testCategoryDTO.getCategoryId()),
            () -> assertEquals(category.getName(), testCategoryDTO.getCategoryName()),
            () -> assertEquals(category.getImageUrl(), testCategoryDTO.getCategoryImage()),
            () -> assertEquals(categoryDTO, testCategoryDTO)
        );
    }

    @Test
    void save_whenCategoryNameAlreadyExists_shouldThrowAlreadyExistsException() {
        when(categoryMapper.toCategory(any(SaveCategoryDTO.class))).thenReturn(category);
        when(categoryRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> categoryService.save(saveCategoryDTO));
    }


    @Test
    void update_whenValidCategory_shouldReturnCategoryDTO() {
        when(categoryRepository.existsById(anyLong())).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toCategoryDTO(any(Category.class))).thenReturn(categoryDTO);
        when(categoryMapper.toCategory(any(CategoryDTO.class))).thenReturn(category);

        CategoryDTO testCategoryDTO = categoryService.update(categoryDTO);

        assertAll("UpdatedCategory",
            () -> assertNotNull(testCategoryDTO),

            () -> assertEquals(categoryDTO, testCategoryDTO)
        );
    }

    @Test
    void update_whenCategoryNotFound_shouldThrowNotFoundException() {
        when(categoryMapper.toCategory(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.update(categoryDTO));
    }

    @Test
    void delete_whenValidIdAndExistingCategory_shouldDeleteCategory() {
        when(categoryRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> categoryService.delete(1L));
    }

    @Test
    void delete_whenCategoryNotFound_shouldThrowNotFoundException() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.delete(1L));
    }

}
