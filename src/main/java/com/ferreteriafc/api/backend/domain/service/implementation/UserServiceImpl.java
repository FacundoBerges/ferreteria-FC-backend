package com.ferreteriafc.api.backend.domain.service.implementation;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteriafc.api.backend.domain.dto.request.RegisterUserDTO;
import com.ferreteriafc.api.backend.domain.dto.request.ChangeUserPasswordDTO;
import com.ferreteriafc.api.backend.domain.dto.response.MessageDTO;
import com.ferreteriafc.api.backend.domain.dto.UserDTO;
import com.ferreteriafc.api.backend.domain.mapper.UserMapper;
import com.ferreteriafc.api.backend.domain.service.IRoleService;
import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.persistence.entity.Role;
import com.ferreteriafc.api.backend.persistence.entity.User;
import com.ferreteriafc.api.backend.persistence.repository.UserRepository;
import com.ferreteriafc.api.backend.web.exception.*;

import static com.ferreteriafc.api.backend.domain.utils.Validation.validateId;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           IRoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                        .findByUsernameOrEmail(username, username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));

        Collection<GrantedAuthority> userAuthorities = getAuthorities(user);

        if (userAuthorities.isEmpty()) {
            userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getHashedPassword())
                    .authorities(userAuthorities)
                    .accountLocked(user.getLocked())
                    .disabled(user.getDisabled())
                    .build();
    }

    @Override
    public UserDetails signUp(RegisterUserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername()))
            throw new AlreadyExistException("User already exists with username: " + userDTO.getUsername());

        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new AlreadyExistException("User already exists with email: " + userDTO.getEmail());

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleService.findByName("USER");
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        roles.add(defaultRole);

        User user = User.builder()
                        .id(null)
                        .username(userDTO.getUsername())
                        .hashedPassword(hashedPassword)
                        .email(userDTO.getEmail())
                        .roles(roles)
                        .locked(false)
                        .disabled(false)
                        .build();

        User savedUser = userRepository.save(user);

        return loadUserByUsername(savedUser.getUsername());
    }

    private Collection<GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {
            String roleName = role.getName().toUpperCase();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + roleName);

            authorities.add(grantedAuthority);
        });

        return authorities;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty())
            throw new EmptyListException("No users found");

        return userMapper.toUserDTO(users);
    }

    @Override
    public UserDTO findById(Integer id) {
        validateId(id);

        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new NotFoundException("User not found with username: " + username));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO update(Integer id, UserDTO userDTO) {
        validateId(id);

        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setDisabled(userDTO.getDisabled());
        user.setLocked(userDTO.getLocked());

        User savedUser = userRepository.save(user);

        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public MessageDTO changePassword(UserDetails userDetails, String username, ChangeUserPasswordDTO changeUserPasswordDTO) {
        if (changeUserPasswordDTO.getOldPassword().equals(changeUserPasswordDTO.getNewPassword()))
            throw new AlreadyExistException("Old password matches new password");

        if (! changeUserPasswordDTO.getNewPassword().equals(changeUserPasswordDTO.getConfirmNewPassword()) )
            throw new PasswordConfirmationMismatchedException("New password doesn't match confirmation password");

        if (!userDetails.getUsername().equals(username))
            throw new ForbiddenException("User not allowed to change password.");

        User user = userRepository
                        .findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()));

        if ( ! passwordEncoder.matches(changeUserPasswordDTO.getOldPassword(), user.getHashedPassword()) )
            throw new InvalidCredentialsException("Wrong old password");

        String newHashedPassword = this.passwordEncoder.encode(changeUserPasswordDTO.getNewPassword());
        user.setHashedPassword(newHashedPassword);

        userRepository.save(user);

        return new MessageDTO("Password changed successfully.");
    }

    @Override
    public void delete(Integer id) {
        validateId(id);

        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        user.setDisabled(true);

        userRepository.save(user);
    }

}
