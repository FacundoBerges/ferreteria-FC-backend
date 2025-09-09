package com.ferreteriafc.api.backend.domain.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ferreteriafc.api.backend.domain.dto.request.ChangeUserPasswordDTO;
import com.ferreteriafc.api.backend.domain.dto.request.RegisterUserDTO;
import com.ferreteriafc.api.backend.domain.dto.response.MessageDTO;
import com.ferreteriafc.api.backend.domain.dto.UserDTO;

public interface IUserService extends UserDetailsService {

    UserDetails signUp(RegisterUserDTO userDTO);
    MessageDTO changePassword(UserDetails userDetails, String username, ChangeUserPasswordDTO changeUserPasswordDTO);
    List<UserDTO> findAll();
    UserDTO findById(Integer id);
    UserDTO save(RegisterUserDTO userDTO);
    UserDTO update(Integer id, UserDTO userDTO);
    void delete(Integer id);

}
