package com.ferreteriafc.api.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.persistence.entity.User;
import com.ferreteriafc.api.backend.persistence.repository.UserRepository;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found by username."));

        //TODO: Implementar funcionalidad.
        return null;
    }
}
