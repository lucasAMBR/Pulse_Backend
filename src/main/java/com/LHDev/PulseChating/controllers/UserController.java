package com.LHDev.PulseChating.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LHDev.PulseChating.services.UserService;
import com.LHDev.PulseChating.utils.AuthUtils;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @PutMapping("/switch_visibility")
    public ResponseEntity<Void> handleSwitchVisibility(){
        UUID userId = AuthUtils.getAuthenticatedUserId();

        userService.switchProfileVisibility(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/deactivate_account")
    public ResponseEntity<Void> handleDeactivateAccount(){
        UUID userId = AuthUtils.getAuthenticatedUserId();

        userService.deactivateAccount(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reactivate_account")
    public ResponseEntity<Void> handleReactivateAccount(){
        UUID userId = AuthUtils.getAuthenticatedUserId();

        userService.reactivateAccount(userId);

        return ResponseEntity.ok().build();
    }
}