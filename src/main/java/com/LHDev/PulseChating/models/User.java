package com.LHDev.PulseChating.models;

import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private UUID id;

    @NotBlank
    private String name;
    
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String avatarUrl;

    @PrePersist
    public void generateId(){
        if(this.id == null){
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }
}
