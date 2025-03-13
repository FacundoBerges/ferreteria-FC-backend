package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.web.dto.UserDTO;
import com.ferreteriafc.api.backend.web.dto.request.RegisterUserDTO;

public interface IUserService {

    UserDTO save(RegisterUserDTO userDTO);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

}
