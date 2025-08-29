package com.ferreteriafc.api.backend.persistence.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriafc.api.backend.persistence.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

}
