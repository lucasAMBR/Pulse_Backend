package com.LHDev.PulseChating.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LHDev.PulseChating.common.DTOs.UserLoginDTO;
import com.LHDev.PulseChating.common.DTOs.UserRegisterDTO;
import com.LHDev.PulseChating.common.enums.AccountStatus;
import com.LHDev.PulseChating.exceptions.custom.EmailAlreadyInUseException;
import com.LHDev.PulseChating.exceptions.custom.InvalidArgumentException;
import com.LHDev.PulseChating.exceptions.custom.LoginFailedException;
import com.LHDev.PulseChating.models.User;
import com.LHDev.PulseChating.utils.CryptoUtils;
import com.LHDev.PulseChating.utils.ImageUtils;

@Service
public class AuthService {
    
    @Autowired
    UserService userService;

    public User registerNewUser(UserRegisterDTO userData){
        String profilePicPath;
        if(userData.getProfilePic() != null){
            if(ImageUtils.isImage(userData.getProfilePic()) && ImageUtils.validateImage(userData.getProfilePic())){
               try{
                    profilePicPath = ImageUtils.saveImage(userData.getProfilePic());
               }catch (Exception ex){
                    throw new RuntimeException("Error while saving");
               } 
            }else{
                throw new InvalidArgumentException("Invalid profile picture!");
            }
        }else{
            profilePicPath = "/profile_pic/default.png";
        }

        if(userService.userExistsByEmail(userData.getEmail())){
            throw new EmailAlreadyInUseException("Email already in Use");
        }

        User newUser = new User(
            userData.getName(), 
            userData.getEmail(), 
            CryptoUtils.encodePassword(userData.getPassword()), 
            profilePicPath, 
            false, 
            false, 
            AccountStatus.ACTIVE
        );

        return userService.saveUser(newUser);
    }

    public User login(UserLoginDTO loginData){
        User user = userService.findUserByEmail(loginData.getEmail());

        if(CryptoUtils.comparePassword(loginData.getPassword(), user.getPassword())){
            return user;
        }else{
            throw new LoginFailedException("Email or Passsword incorrect");
        }
    }

}
