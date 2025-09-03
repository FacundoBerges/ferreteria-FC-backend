package com.ferreteriafc.api.backend.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {

    @JsonProperty(value = "username_or_email")
    @NotBlank(message = "Username or email cannot be blank nor null. Field name: 'username_or_email'")
    @Size(min = Constant.USERNAME_MIN_LENGTH, max = Constant.USERNAME_MAX_LENGTH, message = "Username or email length must be between {min} and {max}.")
    private String usernameOrEmail;

    @NotBlank(message = "Password cannot be blank nor null.")
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH, message = "Password length must be between {min} and {max}.")
    @Pattern(regexp = Constant.PASSWORD_REGEX_PATTERN)
    private String password;

}
