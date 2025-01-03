package com.ferreteriafc.api.backend.web.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ValidationErrorsDTO {

    private Integer status;
    private String error;
    private Map<String, String> errors;

}
