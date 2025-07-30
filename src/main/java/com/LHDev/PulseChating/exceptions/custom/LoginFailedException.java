package com.LHDev.PulseChating.exceptions.custom;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message){
        super(message);
    }
}
