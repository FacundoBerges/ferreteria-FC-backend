package com.ferreteriafc.api.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController()
public class HelloWorldController {

    @Autowired
    private Environment env;

    @GetMapping()
    public String helloWorld() {
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        String url = env.getProperty("spring.datasource.url");

        return "Hello World!".concat(" URL: " + url + " Username: " + username + " Password: " + password);
    }

}
