package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PaymentService {
    Optional<OrderEntity> pay(Long orderId);
    UserEntity donate(int cash);
}
