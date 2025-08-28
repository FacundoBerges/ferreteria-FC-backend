package com.ferreteriafc.api.backend.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RoleDTO {

    @NotNull(message = "Role id cannot be null.")
    @Positive(message = "Role id must be a positive number.")
    private Integer id;

    @JsonProperty(value = "role_name")
    @NotNull(message = "Role name cannot be null.")
    @Size(min = 3, message = "Role name length must be higher than {min}.")
    private String roleName;

}
