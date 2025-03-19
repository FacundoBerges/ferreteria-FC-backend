package com.ferreteriafc.api.backend.web.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {

    @NotNull(message = "User id cannot be null.")
    @Positive(message = "User id must be a positive number.")
    private Integer id;

    @NotBlank(message = "Username cannot be blank or null.")
    @Size(min = Constant.USERNAME_MIN_LENGTH, max = Constant.USERNAME_MAX_LENGTH, message = "Username length must be between {min} and {max} characters.")
    @Pattern(regexp = Constant.USERNAME_REGEX_PATTERN, message = "Username only allows alphabetic characters (a-z & A-Z).")
    private String username;

    @NotBlank(message = "Email cannot be blank or null.")
    @Size(min = Constant.EMAIL_MIN_LENGTH, max = Constant.EMAIL_MAX_LENGTH, message = "Email length must be between {min} and {max} characters.")
    @Email(regexp = Constant.EMAIL_REGEX_PATTERN, message = "Email format is invalid.")
    private String email;

}
