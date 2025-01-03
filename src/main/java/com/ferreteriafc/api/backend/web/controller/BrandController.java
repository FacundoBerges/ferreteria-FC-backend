package com.ferreteriafc.api.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ferreteriafc.api.backend.domain.service.IBrandService;
import com.ferreteriafc.api.backend.domain.service.BrandServiceImpl;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.request.SaveBrandDTO;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final IBrandService brandService;

    @Autowired
    public BrandController(BrandServiceImpl brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllBrands() {
        return new ResponseEntity<>(brandService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addBrand(@RequestBody SaveBrandDTO brandDTO) {
        return new ResponseEntity<>(brandService.save(brandDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> updateBrand(@RequestBody BrandDTO brandDTO) {
        return new ResponseEntity<>(brandService.update(brandDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
