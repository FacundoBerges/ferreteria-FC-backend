package com.ferreteriafc.api.backend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriafc.api.backend.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByCode(String code);

}
