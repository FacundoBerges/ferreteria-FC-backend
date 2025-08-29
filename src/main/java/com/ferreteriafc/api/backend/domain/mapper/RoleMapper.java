package com.ferreteriafc.api.backend.domain.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ferreteriafc.api.backend.domain.dto.RoleDTO;
import com.ferreteriafc.api.backend.persistence.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "roleName")
    })
    RoleDTO toRoleDTO(Role role);

    @InheritInverseConfiguration
    Role toRole(RoleDTO roleDTO);

}
