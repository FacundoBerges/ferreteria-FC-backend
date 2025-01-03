package com.ferreteriafc.api.backend.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SaveBrandDTO {

    @JsonProperty("brand_name")
    private String brandName;

}
