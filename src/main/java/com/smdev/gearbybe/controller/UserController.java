package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.response.JwtResponse;
import com.smdev.gearbybe.model.dto.LoginRequest;
import com.smdev.gearbybe.model.dto.RegistrationRequest;
import com.smdev.gearbybe.model.dto.response.UserResponse;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest registrationRequest){
        JwtResponse response = userService.register(registrationRequest);
        if(response.getToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse response = userService.login(loginRequest);
        if(response.getToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getCurrent(){
        return userService.getCurrent().map(UserResponse::new).get();
    }
}
