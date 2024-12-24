package com.ferreteriafc.api.backend.web.controller;

import com.ferreteriafc.api.backend.domain.service.CategoryService;
import com.ferreteriafc.api.backend.domain.service.ICategoryService;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategories() {
        CategoryDTO dto = new CategoryDTO();

        dto.setCategoryId(1L);
        dto.setCategoryImage("url");
        dto.setCategoryName("hola");

        return new ResponseEntity<>(List.of(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDTO dto = new CategoryDTO();

        dto.setCategoryId(id);
        dto.setCategoryImage("url");
        dto.setCategoryName("hola");

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO category) {
        category.setCategoryId(1L);

        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO category) {
        category.setCategoryId(1L);
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
