package com.ferreteriafc.api.backend.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ErrorDTO {

    private Integer status;
    private String error;
    private String message;

}
