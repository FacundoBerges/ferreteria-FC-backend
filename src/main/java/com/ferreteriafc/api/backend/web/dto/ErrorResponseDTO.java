package com.ferreteriafc.api.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private Integer status;

    private String error;

    private String message;

}
