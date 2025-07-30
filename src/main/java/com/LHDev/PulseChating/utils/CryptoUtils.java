package com.LHDev.PulseChating.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CryptoUtils {
    
    public static String encodePassword(String rawPassword){
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(rawPassword);
    }

    public static Boolean comparePassword(String rawPassword, String encodedPassword){
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(rawPassword, encodedPassword);
    }

}
