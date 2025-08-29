package com.ferreteriafc.api.backend.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class ImageUploadDTO {

    private String message;

    @JsonProperty(value = "file_name")
    private String fileName;

}
