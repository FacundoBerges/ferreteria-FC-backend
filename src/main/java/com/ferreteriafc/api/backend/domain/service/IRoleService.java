package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.persistence.entity.Role;
import com.ferreteriafc.api.backend.web.dto.RoleDTO;

public interface IRoleService {

    Role findByName(String name);
    RoleDTO mapToDto(Role role);

}
