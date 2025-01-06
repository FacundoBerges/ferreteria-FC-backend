package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class SaveProductDTO {

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
