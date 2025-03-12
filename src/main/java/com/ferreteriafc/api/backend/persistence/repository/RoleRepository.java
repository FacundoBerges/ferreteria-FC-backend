package com.ferreteriafc.api.backend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteriafc.api.backend.persistence.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
