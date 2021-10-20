package com.smdev.gearbybe.handler;

import com.smdev.gearbybe.exception.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validation(BindingResult bindingResult){
        return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity token(TokenException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getReason());
    }

}
