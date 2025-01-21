package com.ferreteriafc.api.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import com.ferreteriafc.api.backend.domain.service.FileServiceImpl;
import com.ferreteriafc.api.backend.domain.service.IFileService;
import com.ferreteriafc.api.backend.domain.service.IProductService;
import com.ferreteriafc.api.backend.domain.service.ProductServiceImpl;
import com.ferreteriafc.api.backend.web.dto.ProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.NewProductDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveProductDTO;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;
    private final IFileService fileService;

    @Autowired
    public ProductController(ProductServiceImpl productService, FileServiceImpl fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody @Valid NewProductDTO product,
                                        @RequestPart(name = "image", required = false) MultipartFile file) {
        String filepath = fileService.uploadFile(file);

        SaveProductDTO toSave = new SaveProductDTO(
                                        product.getDescription(),
                                        product.getCode(),
                                        product.getPrice(),
                                        filepath,
                                        product.getBrandId(),
                                        product.getCategoryId());

        return new ResponseEntity<>(productService.save(toSave), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO product,
                                           @RequestPart(required = false) MultipartFile file) {
        String filepath = fileService.uploadFile(file);

        if (filepath != null)
            product.setImageUrl(filepath);

        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
