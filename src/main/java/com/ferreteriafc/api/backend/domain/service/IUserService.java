package com.ferreteriafc.api.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ferreteriafc.api.backend.web.dto.UserDTO;
import com.ferreteriafc.api.backend.web.dto.request.RegisterAndLoginUserDTO;

public interface IUserService extends UserDetailsService {

    UserDTO save(RegisterAndLoginUserDTO userDTO);

    String login(RegisterAndLoginUserDTO userDTO);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

}
