package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.model.dto.UserDetailsImpl;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(s);
        UserEntity user = userEntity.orElseThrow(() -> new UsernameNotFoundException(s));
        return new UserDetailsImpl(user);
    }

}
