package com.LHDev.PulseChating.models;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.LHDev.PulseChating.common.enums.AccountStatus;
import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
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

    @NotNull
    private boolean isVerified;

    @NotNull
    private AccountStatus accountStatus;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    public void generateId(){
        if(this.id == null){
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }
}
