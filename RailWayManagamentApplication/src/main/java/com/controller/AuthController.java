package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginRequest;
import com.dto.LoginResponseDTO;
import com.dto.RegisterRequest;
import com.service.AuthService;
import com.service.UserService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthService userService ;
    
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request.getUsername() , request.getPassword());
    }
    
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequest request) {
        return userService.loginUser(request.getUsername() , request.getPassword());
    }
    @GetMapping("test")
    public String Test() {
    	return "okay";
    }
}
