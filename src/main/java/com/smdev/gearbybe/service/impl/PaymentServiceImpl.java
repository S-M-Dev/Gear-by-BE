package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.model.entity.OrderPositionEntity;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.repository.OrderRepository;
import com.smdev.gearbybe.repository.UserRepository;
import com.smdev.gearbybe.service.OrderService;
import com.smdev.gearbybe.service.PaymentService;
import com.smdev.gearbybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(UserService userService,
                              OrderService orderService,
                              UserRepository userRepository,
                              OrderRepository orderRepository) {
        this.userService = userService;
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderEntity> pay(Long orderId) {
        UserEntity current = userService.getCurrent().get();
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if(orderEntity.isEmpty()){
            return orderEntity;
        }

        int summary = orderEntity
                .get()
                .getOrderPositions()
                .stream()
                .mapToInt(o -> o.getAmount() * o.getPart().getPrice())
                .sum();

        if(current.getCash() >= summary){
            current.setCash(current.getCash() - summary);
            userRepository.save(current);
            orderEntity = orderService.approve(orderId);
        }

        return orderEntity;
    }

    @Override
    public UserEntity donate(int cash) {
        UserEntity current = userService.getCurrent().get();
        current.setCash(current.getCash() + cash);
        userRepository.save(current);
        return current;
    }
}
