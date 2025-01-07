package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ferreteriafc.api.backend.domain.utils.Constant;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;

@NoArgsConstructor @AllArgsConstructor @Data
public class SaveProductDTO {

    @JsonProperty("product_description")
    @NotBlank(message = "Description cannot be blank.")
    protected String description;

    @JsonProperty("product_code")
    @Size(min = 1, max = Constant.PRODUCTS_DESCRIPTION_MAX_LENGTH, message = "Code length must be between {min} and {max}.")
    @NotBlank(message = "Code cannot be blank.")
    protected String code;

    @JsonProperty("product_price")
    protected Double price;

    @JsonProperty("product_image_url")
    protected String imageUrl;

    @JsonProperty("product_brand")
    protected BrandDTO brandDTO;

    @JsonProperty("product_category")
    protected CategoryDTO categoryDTO;

}
