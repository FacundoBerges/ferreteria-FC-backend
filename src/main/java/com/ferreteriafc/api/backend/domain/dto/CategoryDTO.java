package com.ferreteriafc.api.backend.domain.dto;

import jakarta.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@NoArgsConstructor @AllArgsConstructor @Data
public class CategoryDTO {

    @JsonProperty("category_id")
    @NotNull(message = "Id must not be null. Field name: 'category_id'")
    @Positive(message = "Id must be a positive number.")
    private Integer categoryId;

    @JsonProperty("category_name")
    @NotBlank(message = "Name cannot be blank. Field name: 'category_name'")
    @Size(min = 1, max = Constant.CATEGORIES_NAME_MAX_LENGTH, message = "Name length must be between {min} and {max} characters.")
    private String categoryName;

    @JsonProperty("category_image")
    private String categoryImage;

}
