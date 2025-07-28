package com.LHDev.PulseChating.exceptions.custom;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String message){
        super(message);
    }
}
