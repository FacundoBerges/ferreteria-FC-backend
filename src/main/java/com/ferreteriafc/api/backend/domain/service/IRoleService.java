package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.domain.dto.RoleDTO;
import com.ferreteriafc.api.backend.persistence.entity.Role;

public interface IRoleService {

    Role findByName(String name);
    RoleDTO mapToDto(Role role);

}
