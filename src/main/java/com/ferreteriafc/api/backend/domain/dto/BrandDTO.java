package com.ferreteriafc.api.backend.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class BrandDTO {

    @JsonProperty("brand_id")
    @NotNull(message = "Id must not be null.")
    @Positive(message = "Id must be a positive number.")
    private Integer brandId;

    @JsonProperty("brand_name")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = Constant.BRANDS_NAME_MAX_LENGTH, message = "Name length must be between {min} and {max} characters.")
    private String brandName;

}
