package com.LHDev.PulseChating.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LHDev.PulseChating.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}
