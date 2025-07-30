package com.LHDev.PulseChating.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LHDev.PulseChating.common.DTOs.UserLoginDTO;
import com.LHDev.PulseChating.common.DTOs.UserRegisterDTO;
import com.LHDev.PulseChating.models.User;
import com.LHDev.PulseChating.security.JwtService;
import com.LHDev.PulseChating.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<User> handleRegisterNewUser(@Valid @ModelAttribute UserRegisterDTO userData){
        User newUser = authService.registerNewUser(userData);

        URI location = URI.create("/api/v1/user/" + newUser.getId());

        return ResponseEntity.created(location).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> handleLogin(@Valid @ModelAttribute UserLoginDTO loginData, HttpServletResponse response){
        User user = authService.login(loginData);

        String csrfToken = UUID.randomUUID().toString();
        String jwtToken = jwtService.generateToken(user.getId(), csrfToken);

        ResponseCookie jwtCookie = ResponseCookie.from("token", jwtToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("None")
            .build();
        
        ResponseCookie csrfCookie = ResponseCookie.from("csfr_token", csrfToken)
            .httpOnly(false)
            .secure(true)
            .path("/")
            .sameSite("None")
            .build();
        
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, csrfCookie.toString());

        return ResponseEntity.ok().build();
    }
}
