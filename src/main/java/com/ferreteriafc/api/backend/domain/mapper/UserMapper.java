package com.ferreteriafc.api.backend.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ferreteriafc.api.backend.domain.dto.UserDTO;
import com.ferreteriafc.api.backend.persistence.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "username", target = "username"),
        @Mapping(source = "locked", target = "locked"),
        @Mapping(source = "disabled", target = "disabled")
    })
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTO(List<User> users);

}
