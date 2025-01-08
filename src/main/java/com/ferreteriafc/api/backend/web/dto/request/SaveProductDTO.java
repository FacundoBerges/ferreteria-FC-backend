package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ferreteriafc.api.backend.domain.utils.Constant;

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

    @JsonProperty("product_brand_id")
    @NotNull(message = "Brand id cannot be null.")
    @Min(value = 1, message = "Brand id cannot be less than {value}.")
    protected Integer brandId;

    @JsonProperty("product_category_id")
    @NotNull(message = "Brand id cannot be null.")
    @Min(value = 1, message = "Brand id cannot be less than {value}.")
    protected Integer categoryId;

}
