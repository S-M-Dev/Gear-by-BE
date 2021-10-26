package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.response.JwtResponse;
import com.smdev.gearbybe.model.dto.LoginRequest;
import com.smdev.gearbybe.model.dto.RegistrationRequest;
import com.smdev.gearbybe.model.dto.response.UserResponse;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered successfully",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "409", description = "User with same email is already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest registrationRequest){
        JwtResponse response = userService.register(registrationRequest);
        if(response.getToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Login as existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logged in successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse response = userService.login(loginRequest);
        if(response.getToken().isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get current authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "On success",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getCurrent(){
        return userService.getCurrent().map(UserResponse::new).get();
    }
}
