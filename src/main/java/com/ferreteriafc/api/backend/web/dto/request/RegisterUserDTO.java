package com.ferreteriafc.api.backend.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterUserDTO {

    private String username;

    private String password;

    private String email;

}
