package com.ferreteriafc.api.backend.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "Username cannot be blank nor null.")
    @Size(min = Constant.USERNAME_MIN_LENGTH, max = Constant.USERNAME_MAX_LENGTH, message = "Username length must be between {min} and {max}.")
    @Pattern(regexp = Constant.USERNAME_REGEX_PATTERN, message = "Username only allows alphabetic characters (a-z & A-Z).")
    private String username;

    @NotBlank(message = "Password cannot be blank nor null.")
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH, message = "Password length must be between {min} and {max}.")
    @Pattern(regexp = Constant.PASSWORD_REGEX_PATTERN)
    private String password;

    @NotBlank(message = "Email cannot be blank nor null.")
    @Size(min = Constant.EMAIL_MIN_LENGTH, max = Constant.EMAIL_MAX_LENGTH, message = "Email length must be between {min} and {max}.")
    @Email(message = "Invalid email input.")
    private String email;

}
