package com.ferreteriafc.api.backend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorsDTO {

    private Integer status;
    private String error;
    private Map<String, String> errors;

}
