package com.ferreteriafc.api.backend.domain.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.domain.mapper.RoleMapper;
import com.ferreteriafc.api.backend.domain.mapper.UserMapper;
import com.ferreteriafc.api.backend.persistence.entity.Role;
import com.ferreteriafc.api.backend.persistence.entity.User;
import com.ferreteriafc.api.backend.persistence.repository.UserRepository;
import com.ferreteriafc.api.backend.web.dto.RoleDTO;
import com.ferreteriafc.api.backend.web.dto.UserDTO;
import com.ferreteriafc.api.backend.web.dto.request.RegisterUserDTO;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           IRoleService roleService,
                           RoleMapper roleMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found by username: " + username));

        Collection<GrantedAuthority> userAuthorities = getAuthorities(user);

        if (userAuthorities.isEmpty())
            throw new NotFoundException("No authorities found with username: " + username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getHashedPassword(),
                userAuthorities
        );
    }

    @Override
    public UserDTO save(RegisterUserDTO userDTO) {
        Set<Role> roles = new HashSet<>();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        RoleDTO defaultRole = roleService.findByName("ROLE_USER");

        roles.add(roleMapper.roleDTOToRole(defaultRole));

        User user = new User(
            null,
            userDTO.getUsername(),
            hashedPassword,
            userDTO.getEmail(),
            roles
        );

        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by username: " + username));

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));

        return userMapper.userToUserDTO(user);
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles()
            .forEach(role -> {
                String roleName = role.getName().toUpperCase();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);

                authorities.add(grantedAuthority);
            });

        return authorities;
    }
}
