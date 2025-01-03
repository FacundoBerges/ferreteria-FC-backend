package com.ferreteriafc.api.backend.persistence.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriafc.api.backend.persistence.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
