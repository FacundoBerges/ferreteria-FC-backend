package com.ferreteriafc.api.backend.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class SaveBrandDTO {

    @JsonProperty("brand_name")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = Constant.BRANDS_NAME_MAX_LENGTH, message = "Name length must be between {min} and {max} characters.")
    private String brandName;

}
