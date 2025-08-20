package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectController {

    @GetMapping("/protected")
    public String helloProtected() {
        return "Authenticated user";
    }
}
