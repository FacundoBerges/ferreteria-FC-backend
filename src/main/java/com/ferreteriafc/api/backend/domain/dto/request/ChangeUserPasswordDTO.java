package com.ferreteriafc.api.backend.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangeUserPasswordDTO {

    @JsonProperty(value = "old_password")
    @NotBlank(message = "Password cannot be blank nor null. Field name: 'old_password'")
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH, message = "Password length must be between {min} and {max}.")
    @Pattern(regexp = Constant.PASSWORD_REGEX_PATTERN, message = "Password must contain at least a lowercase character (a-z), an uppercase character (A-Z) and a number (0-9).")
    private String oldPassword;

    @JsonProperty(value = "new_password")
    @NotBlank(message = "Password cannot be blank nor null. Field name: 'new_password'")
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH, message = "Password length must be between {min} and {max}.")
    @Pattern(regexp = Constant.PASSWORD_REGEX_PATTERN, message = "Password must contain at least a lowercase character (a-z), an uppercase character (A-Z) and a number (0-9).")
    private String newPassword;

    @JsonProperty(value = "confirm_password")
    @NotBlank(message = "Password cannot be blank nor null. Field name: 'confirm_password'")
    @Size(min = Constant.PASSWORD_MIN_LENGTH, max = Constant.PASSWORD_MAX_LENGTH, message = "Password length must be between {min} and {max}.")
    @Pattern(regexp = Constant.PASSWORD_REGEX_PATTERN, message = "Password must contain at least a lowercase character (a-z), an uppercase character (A-Z) and a number (0-9).")
    private String confirmNewPassword;
}
