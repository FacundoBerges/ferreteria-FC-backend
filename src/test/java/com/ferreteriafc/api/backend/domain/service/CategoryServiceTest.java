package com.ferreteriafc.api.backend.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.ferreteriafc.api.backend.domain.mapper.CategoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ferreteriafc.api.backend.domain.utils.MockObjectFactory;
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
    @DisplayName("Method: findAll() - when existing categories should: return list of categoriesDTO")
    void findAll_whenExistingCategories_shouldReturnCategoriesDTOs() {
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
    @DisplayName("Method: findAll() - when no categories in database should throw NotFoundException")
    void findAll_whenNoCategories_shouldThrowNotFoundException() {
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, categoryService::findAll);
    }


    @Test
    @DisplayName("Method: findById() - when valid ID and category exists in database should return corresponding categoryDTO")
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
    @DisplayName("Method: findById() - when valid ID but category doesn't exist in database should throw NotFoundException")
    void findById_whenValidIdAndNoExistingEntity_shouldThrowNotFoundException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.findById(1L));
    }

    @Test
    @DisplayName("Method: findById() - when ID is null should throw InvalidIdException")
    void findById_whenIdIsNull_shouldThrowInvalidIdException() {
        assertThrows(InvalidIdException.class, () -> categoryService.findById(null));
    }

    @Test
    @DisplayName("Method: findById() - when ID is negative or zero should throw InvalidIdException")
    void findById_whenIdIsNegativeOrZero_shouldThrowInvalidIdException() {
        assertThrows(InvalidIdException.class, () -> categoryService.findById(0L));
        assertThrows(InvalidIdException.class, () -> categoryService.findById(-1L));
    }


    @Test
    @DisplayName("Method: save() - when is valid category should return categoryDTO")
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
    @DisplayName("Method: save() - when category name is already in database should throw AlreadyExistsException")
    void save_whenCategoryNameAlreadyExists_shouldThrowAlreadyExistsException() {
        when(categoryMapper.toCategory(any(SaveCategoryDTO.class))).thenReturn(category);
        when(categoryRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> categoryService.save(saveCategoryDTO));
    }


    @Test
    @DisplayName("Method: update() - when is valid category should return categoryDTO")
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
    @DisplayName("Method: update() - when the category is not in database should throw NotFoundException")
    void update_whenCategoryNotFound_shouldThrowNotFoundException() {
        when(categoryMapper.toCategory(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.update(categoryDTO));
    }

    @Test
    @DisplayName("Method: delete() - when is valid ID and category exists in database, should delete category")
    void delete_whenValidIdAndExistingCategory_shouldDeleteCategory() {
        when(categoryRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> categoryService.delete(1L));
        verify(categoryRepository, times(1)).existsById(anyLong());
        verify(categoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Method: delete() - when invalid ID or category doesn't exist in database, should throw NotFoundException")
    void delete_whenCategoryNotFound_shouldThrowNotFoundException() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> categoryService.delete(1L));
        verify(categoryRepository, times(1)).existsById(anyLong());
        verify(categoryRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Method: delete() - when ID is null, should throw InvalidIdException")
    void delete_whenIdIsNull_shouldThrowInvalidIdException() {
        assertThrows(InvalidIdException.class, () -> categoryService.delete(null));
        verify(categoryRepository, never()).existsById(anyLong());
        verify(categoryRepository, never()).deleteById(isNull());
    }

    @Test
    @DisplayName("Method: delete() - when ID is negative or zero, should throw InvalidIdException")
    void delete_whenIdIsNegativeOrZero_shouldThrowInvalidIdException() {
        assertAll(
            () -> assertThrows(InvalidIdException.class, () -> categoryService.delete(0L)),
            () -> assertThrows(InvalidIdException.class, () -> categoryService.delete(-1L))
        );
        verify( categoryRepository, never()).existsById(0L);
        verify(categoryRepository, never()).deleteById(-1L);
        verify(categoryRepository, never()).deleteById(0L);
        verify(categoryRepository, never()).deleteById(-1L);
    }
}
