package com.LHDev.PulseChating.common.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    @Size(max = 120, message = "maximum size reached")
    private String email;

    
    @Size(min = 8, max = 32, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[^a-zA-Z0-9]).{8,}$", 
    message = "The password must have: one Uppercase Character, one lowercase, one number, and one special character")
    private String password;
}
