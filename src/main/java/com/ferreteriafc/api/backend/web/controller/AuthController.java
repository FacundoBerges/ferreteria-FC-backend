package com.ferreteriafc.api.backend.web.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ferreteriafc.api.backend.domain.service.IUserService;
import com.ferreteriafc.api.backend.web.dto.request.RegisterAndLoginUserDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    @Autowired
    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterAndLoginUserDTO user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid RegisterAndLoginUserDTO user) {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

}
