package com.ferreteriafc.api.backend.domain.service.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.ferreteriafc.api.backend.web.dto.response.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.domain.mapper.RoleMapper;
import com.ferreteriafc.api.backend.domain.mapper.UserMapper;
import com.ferreteriafc.api.backend.domain.service.IJwtService;
import com.ferreteriafc.api.backend.domain.service.IRoleService;
import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.domain.utils.Validation;
import com.ferreteriafc.api.backend.persistence.entity.Role;
import com.ferreteriafc.api.backend.persistence.entity.User;
import com.ferreteriafc.api.backend.persistence.repository.UserRepository;
import com.ferreteriafc.api.backend.web.dto.RoleDTO;
import com.ferreteriafc.api.backend.web.dto.UserDTO;
import com.ferreteriafc.api.backend.web.dto.request.RegisterAndLoginUserDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IRoleService roleService;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           IRoleService roleService,
                           RoleMapper roleMapper,
                           PasswordEncoder passwordEncoder,
                           IJwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
    public UserDTO save(RegisterAndLoginUserDTO userDTO) {
        Validation.validateEmail(userDTO.getEmail());
        Validation.validatePassword(userDTO.getPassword());

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new AlreadyExistException("User already exists with username: " + userDTO.getUsername());

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new AlreadyExistException("User already exists with email: " + userDTO.getEmail());

        Set<Role> roles = new HashSet<>();

        RoleDTO defaultRole = roleService.findByName("ROLE_USER");
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        roles.add(roleMapper.toRole(defaultRole));

        User user = User.builder()
                        .id(null)
                        .username(userDTO.getUsername())
                        .hashedPassword(hashedPassword)
                        .email(userDTO.getEmail())
                        .roles(roles)
                        .build();

        return userMapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public AuthToken login(RegisterAndLoginUserDTO userDTO) {
        Validation.validateEmail(userDTO.getEmail());
        Validation.validatePassword(userDTO.getPassword());

        String username = userDTO.getUsername();
        UserDetails userDetails = loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);

        return new AuthToken(token);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found by username: " + username));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email: " + email));

        return userMapper.toUserDTO(user);
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
