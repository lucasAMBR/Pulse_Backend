package com.LHDev.PulseChating.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.LHDev.PulseChating.exceptions.custom.EmailAlreadyInUseException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + " : " + error.getDefaultMessage())
            .toList();
        
            return ResponseEntity.badRequest().body(new ErrorMessage(400, "Validation Failed", errors));
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorMessage> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(409, "Conflict",List.of(ex.getMessage())));
    }

}
