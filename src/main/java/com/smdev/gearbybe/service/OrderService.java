package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.entity.OrderEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<OrderEntity> getAllForUserById(Long userId);
    Optional<OrderEntity> create(OrderCreateRequest orderCreateRequest);
}
