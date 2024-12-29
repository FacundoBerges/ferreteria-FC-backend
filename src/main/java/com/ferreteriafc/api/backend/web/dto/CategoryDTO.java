package com.ferreteriafc.api.backend.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ferreteriafc.api.backend.domain.utils.Constant.CATEGORIES_NAME_MAX_LENGTH;
import static com.ferreteriafc.api.backend.domain.utils.Constant.CATEGORIES_URL_MAX_LENGTH;

@NoArgsConstructor @Data
public class CategoryDTO {

    @JsonProperty("category_id")
    @Positive(message = "Id must be a positive number.")
    private Long categoryId;

    @JsonProperty("category_name")
    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 1, max = CATEGORIES_NAME_MAX_LENGTH, message = "Name length must be between {min} and {max} characters.")
    private String categoryName;

    @JsonProperty("category_image")
    @NotBlank(message = "Image URL cannot be blank.")
    @Size(min = 1, max = CATEGORIES_URL_MAX_LENGTH, message = "Image URL length must be between {min} and {max} characters.")
    private String categoryImage;

}
