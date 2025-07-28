package com.LHDev.PulseChating.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/segundo")
public class SegundoController {
    
    @GetMapping("/teste")
    public String handleTeste(){
        return "Funciona!";
    }
}