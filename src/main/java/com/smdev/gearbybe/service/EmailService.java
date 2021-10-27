package com.smdev.gearbybe.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendRecoveryCode(String code, String email);
}
