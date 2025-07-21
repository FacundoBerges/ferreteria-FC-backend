package com.ferreteriafc.api.backend.domain.service;

import com.ferreteriafc.api.backend.web.dto.response.AuthToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ferreteriafc.api.backend.web.dto.UserDTO;
import com.ferreteriafc.api.backend.web.dto.request.RegisterAndLoginUserDTO;

public interface IUserService extends UserDetailsService {

    UserDTO save(RegisterAndLoginUserDTO userDTO);

    AuthToken login(RegisterAndLoginUserDTO userDTO);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

}
