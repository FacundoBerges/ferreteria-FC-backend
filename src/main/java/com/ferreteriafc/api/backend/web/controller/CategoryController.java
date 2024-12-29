package com.ferreteriafc.api.backend.web.controller;

import com.ferreteriafc.api.backend.domain.service.CategoryServiceImpl;
import com.ferreteriafc.api.backend.domain.service.ICategoryService;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @Valid
            @NotNull(message = "Id cannot be null.")
            @Positive(message = "Id must be bigger than 0.")
            @PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryDTO category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDTO category) {
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @Valid
            @NotNull(message = "Id cannot be null.")
            @Positive(message = "Id must be bigger than 0.")
            @PathVariable Long id) {
        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
