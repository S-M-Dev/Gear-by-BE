package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.mapper.UserMapper;
import com.smdev.gearbybe.model.dto.UserDataInput;
import com.smdev.gearbybe.model.dto.response.JwtResponse;
import com.smdev.gearbybe.model.dto.LoginRequest;
import com.smdev.gearbybe.model.dto.RegistrationRequest;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.repository.UserRepository;
import com.smdev.gearbybe.service.UserService;
import com.smdev.gearbybe.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public JwtResponse register(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            return new JwtResponse("");
        }

        UserEntity user = UserMapper.registrationToUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(registrationRequest.getEmail());
        authenticate(userDetails);
        String jwt = JWTUtils.generate(userDetails);
        return new JwtResponse(jwt);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(loginRequest.getEmail());
        if(userEntity.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userEntity.get().getPassword())){
            return new JwtResponse("");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        authenticate(userDetails);
        String jwt = JWTUtils.generate(userDetails);
        return new JwtResponse(jwt);
    }

    @Override
    public Optional<UserEntity> getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(authentication)){
            return Optional.empty();
        }

        return userRepository.findByEmail(authentication.getName());
    }

    @Override
    public JwtResponse resetPassword(Long id, String password) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()){
            return new JwtResponse("");
        }

        UserEntity user = userEntity.get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        authenticate(userDetails);
        String jwt = JWTUtils.generate(userDetails);
        return new JwtResponse(jwt);
    }

    @Override
    public UserEntity setData(UserDataInput userDataInput) {
        UserEntity current = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        current.setFullName(userDataInput.getFullName());
        current.setAddress(userDataInput.getAddress());
        current.setPhoneNumber(userDataInput.getPhoneNumber());
        userRepository.save(current);

        return current;
    }

    private void authenticate(UserDetails userDetails){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                )
        );
    }
}
