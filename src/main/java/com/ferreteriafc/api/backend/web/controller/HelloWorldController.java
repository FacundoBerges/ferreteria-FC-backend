package com.ferreteriafc.api.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController()
public class HelloWorldController {

    @GetMapping()
    public String helloWorld() {
        return "Hello World!";
    }

}
