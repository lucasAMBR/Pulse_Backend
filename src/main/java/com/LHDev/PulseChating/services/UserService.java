package com.LHDev.PulseChating.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LHDev.PulseChating.common.enums.AccountStatus;
import com.LHDev.PulseChating.exceptions.custom.NotFoundException;
import com.LHDev.PulseChating.models.User;
import com.LHDev.PulseChating.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public User findUserByid(UUID userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public boolean userExistsById(UUID userId){
        return userRepository.existsById(userId);
    } 

    public boolean userExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void switchProfileVisibility(UUID userId){
        User user = findUserByid(userId);

        user.setPrivateProfile(!user.isPrivateProfile());

        saveUser(user);
    }

    public void deactivateAccount(UUID userId){
        User user = findUserByid(userId);

        if(user.getAccountStatus() == AccountStatus.ACTIVE){
            user.setAccountStatus(AccountStatus.DEACTIVATED);
            saveUser(user);
        }else{
            throw new RuntimeException("Não é possivel desativar uma conta ja desativada");
        }
    }

    public void reactivateAccount(UUID userId){
        User user = findUserByid(userId);

        if(user.getAccountStatus() == AccountStatus.DEACTIVATED){
            user.setAccountStatus(AccountStatus.ACTIVE);
            saveUser(user);
        }else{
            throw new RuntimeException("Não é possivel ativar uma conta ja ativada");
        }
    }
}
