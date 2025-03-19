package com.ferreteriafc.api.backend.domain.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String generateToken(UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

    String getUsernameFromToken(String token);

}
