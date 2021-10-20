package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.JwtResponse;
import com.smdev.gearbybe.model.dto.LoginRequest;
import com.smdev.gearbybe.model.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    JwtResponse register(RegistrationRequest registrationRequest);
    JwtResponse login(LoginRequest loginRequest);
}
