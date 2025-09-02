package com.ferreteriafc.api.backend.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    @JsonProperty("product_id")
    @Min(value = 1, message = "Id must be {value} o higher.")
    @NotNull(message = "Id cannot be null. Field name: 'product_id'")
    private Integer id;

    @JsonProperty("product_description")
    @Size(min = 1, max = Constant.PRODUCTS_DESCRIPTION_MAX_LENGTH, message = "Description length must be between {min} and {max}.")
    @NotBlank(message = "Description cannot be blank. Field name: 'product_description'")
    private String description;

    @JsonProperty("product_code")
    @Size(min = 1, max = Constant.PRODUCTS_CODE_MAX_LENGTH, message = "Code length must be between {min} and {max}.")
    @NotBlank(message = "Code cannot be blank. Field name: 'product_code'")
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
