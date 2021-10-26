package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<OrderEntity> getAllForUserById(Long userId);
    List<OrderEntity> getAllForCurrent();
    Optional<OrderEntity> create(OrderCreateRequest orderCreateRequest);
    boolean cancel(Long id);
    Optional<OrderEntity> updateOrder(Long id, OrderCreateRequest orderCreateRequest);
    Optional<OrderEntity> approve(Long id);
}
