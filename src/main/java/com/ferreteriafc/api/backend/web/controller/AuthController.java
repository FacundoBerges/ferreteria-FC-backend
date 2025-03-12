package com.ferreteriafc.api.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ferreteriafc.api.backend.domain.service.UserDetailsServiceImpl;
import com.ferreteriafc.api.backend.web.dto.request.SaveUserDTO;
import com.ferreteriafc.api.backend.web.dto.UserDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SaveUserDTO user) {
        //TODO: Implement method.
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        //TODO: Implement method.
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
