package com.ferreteriafc.api.backend.domain.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.domain.mapper.RoleMapper;
import com.ferreteriafc.api.backend.domain.service.IRoleService;
import com.ferreteriafc.api.backend.persistence.entity.Role;
import com.ferreteriafc.api.backend.persistence.repository.RoleRepository;
import com.ferreteriafc.api.backend.web.dto.RoleDTO;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = roleRepository
                        .findByName(name)
                        .orElseThrow(()-> new NotFoundException("Role not found"));

        return roleMapper.toRoleDTO(role);
    }

}
