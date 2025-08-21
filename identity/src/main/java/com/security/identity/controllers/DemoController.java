package com.security.identity.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/api/success")
    public String success(Principal principal) {
        return "Login successful! User: " + principal.getName();
    }

    @GetMapping("/")
    public String home() {
        return "<a href='/oauth2/authorization/google'>Login with Google</a>";
    }

}