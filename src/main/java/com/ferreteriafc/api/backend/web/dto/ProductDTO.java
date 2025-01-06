package com.ferreteriafc.api.backend.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    @JsonProperty("product_id")
    private Integer id;

    @JsonProperty("product_description")
    protected String description;

    @JsonProperty("product_code")
    protected String code;

    @JsonProperty("product_price")
    protected Double price;

    @JsonProperty("product_image_url")
    protected String imageUrl;

    @JsonProperty("product_brand_id")
    protected Integer brandId;

    @JsonProperty("product_category_id")
    protected Integer categoryId;

}
