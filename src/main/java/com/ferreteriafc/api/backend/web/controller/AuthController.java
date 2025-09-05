package com.ferreteriafc.api.backend.web.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ferreteriafc.api.backend.domain.dto.request.ChangeUserPasswordDTO;
import com.ferreteriafc.api.backend.domain.dto.request.LoginUserDTO;
import com.ferreteriafc.api.backend.domain.dto.request.RegisterUserDTO;
import com.ferreteriafc.api.backend.domain.dto.response.AuthToken;
import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.web.security.jwt.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUserService userService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDTO user) {
        String token = jwtUtils.generateToken(userService.signUp(user));

        return new ResponseEntity<>(new AuthToken(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDTO user) {
        Authentication usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsernameOrEmail(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        AuthToken authToken = new AuthToken(jwtUtils.generateToken(userDetails));

        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }

    @PutMapping("/change-password/{username}")
    public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody @Valid ChangeUserPasswordDTO changeUserPasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userService.changePassword(userDetails, username, changeUserPasswordDTO), HttpStatus.OK);
    }

}
