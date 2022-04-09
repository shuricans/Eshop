package ru.geekbrains.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login", description = "Resource to get authentication information")
@RequestMapping("/v1")
@RestController
public class LoginResource {

    @GetMapping("/login")
    public User login(Authentication auth) {
        return (User) auth.getPrincipal();
    }
}
