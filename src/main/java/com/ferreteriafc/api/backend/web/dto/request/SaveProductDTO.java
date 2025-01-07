package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ferreteriafc.api.backend.web.dto.BrandDTO;
import com.ferreteriafc.api.backend.web.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

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

    @JsonProperty("product_brand")
    protected BrandDTO brandDTO;

    @JsonProperty("product_category")
    protected CategoryDTO categoryDTO;

}
