package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.response.JwtResponse;
import com.smdev.gearbybe.model.dto.LoginRequest;
import com.smdev.gearbybe.model.dto.RegistrationRequest;
import com.smdev.gearbybe.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    JwtResponse register(RegistrationRequest registrationRequest);
    JwtResponse login(LoginRequest loginRequest);
    Optional<UserEntity> getCurrent();
}
