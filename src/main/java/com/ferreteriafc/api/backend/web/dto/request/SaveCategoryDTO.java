package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@NoArgsConstructor @AllArgsConstructor @Data
public class SaveCategoryDTO {

    @JsonProperty("category_name")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = Constant.CATEGORIES_NAME_MAX_LENGTH, message = "Name length must be between {min} and {max} characters.")
    private String categoryName;

    @JsonProperty("category_image")
    @NotEmpty(message = "Image URL cannot be empty.")
    @Size(min = 1, max = Constant.CATEGORIES_URL_MAX_LENGTH, message = "Image URL length must be between {min} and {max} characters.")
    private String categoryImage;

}
