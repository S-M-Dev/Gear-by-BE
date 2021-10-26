package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderEntity> getAllForUserById(Long userId);
    OrderEntity create(OrderCreateRequest orderCreateRequest);
}
