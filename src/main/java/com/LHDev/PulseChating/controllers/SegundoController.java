package com.LHDev.PulseChating.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LHDev.PulseChating.models.User;
import com.LHDev.PulseChating.services.UserService;

@RestController
@RequestMapping("/api/v1/segundo")
public class SegundoController {
    
    @Autowired
    UserService userService;

    @GetMapping("/teste")
    public String handleTeste(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            throw new RuntimeException("Erro de autenticação");
        }

        UUID userId = (UUID) auth.getPrincipal();

        User user = userService.findUserByid(userId);

        return "Funciona Autenticado! Olá " + user.getName();
    }
}