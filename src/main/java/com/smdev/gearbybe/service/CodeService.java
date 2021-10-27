package com.smdev.gearbybe.service;

import org.springframework.stereotype.Service;

@Service
public interface CodeService {
    boolean generate(String email);
    boolean activate(String email, String code);
}
