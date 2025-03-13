package com.ferreteriafc.api.backend.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ferreteriafc.api.backend.persistence.entity.User;
import com.ferreteriafc.api.backend.web.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "username", target = "username"),
    })
    UserDTO userToUserDTO(User user);

}
