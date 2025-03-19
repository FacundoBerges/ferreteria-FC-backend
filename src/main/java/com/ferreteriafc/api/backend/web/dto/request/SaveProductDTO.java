package com.ferreteriafc.api.backend.web.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;
import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

@NoArgsConstructor @AllArgsConstructor @Data
public class SaveProductDTO {

    @JsonProperty("product_description")
    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @JsonProperty("product_code")
    @Size(min = 1, max = Constant.PRODUCTS_CODE_MAX_LENGTH, message = "Code length must be between {min} and {max}.")
    @NotBlank(message = "Code cannot be blank.")
    private String code;

    @JsonProperty("product_image_url")
    private String imageUrl;

    @JsonProperty("product_brand")
    @Valid
    private BrandDTO brandDTO;

    @JsonProperty("product_category")
    @NotNull(message = "Category cannot be null.")
    @Valid
    private CategoryDTO categoryDTO;

}
