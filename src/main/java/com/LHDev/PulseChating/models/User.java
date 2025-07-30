package com.LHDev.PulseChating.models;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.LHDev.PulseChating.common.enums.AccountStatus;
import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @NotNull
    private String avatarUrl;

    @NotNull
    private boolean isPrivateProfile;

    @NotNull
    private boolean isVerified;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    public User(@NotBlank String name, @Email @NotBlank String email, @NotBlank String password,
            @NotNull String avatarUrl, @NotNull boolean isPrivateProfile, @NotNull boolean isVerified,
            @NotNull AccountStatus accountStatus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.isPrivateProfile = isPrivateProfile;
        this.isVerified = isVerified;
        this.accountStatus = accountStatus;
    }

    
}
