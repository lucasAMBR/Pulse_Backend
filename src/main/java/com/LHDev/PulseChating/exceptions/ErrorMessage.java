package com.LHDev.PulseChating.exceptions;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int status;
    private String message;
    private List<?> errors;
    private Instant timestamp;

    public ErrorMessage(int status, String message, List<?> errors){
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
    }
}
