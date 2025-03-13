package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.web.dto.RoleDTO;

public interface IRoleService {

    RoleDTO findByName(String name);

}
