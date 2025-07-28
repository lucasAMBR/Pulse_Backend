package com.LHDev.PulseChating.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teste")
public class TesteController {
    
    @GetMapping
    public String handleTeste(){
        return "Funciona!";
    }
}
