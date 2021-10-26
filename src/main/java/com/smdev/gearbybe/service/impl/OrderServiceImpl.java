package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.exception.OrderException;
import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.model.entity.OrderPositionEntity;
import com.smdev.gearbybe.model.entity.PartEntity;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.repository.OrderPositionRepository;
import com.smdev.gearbybe.repository.OrderRepository;
import com.smdev.gearbybe.repository.PartRepository;
import com.smdev.gearbybe.service.OrderService;
import com.smdev.gearbybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderPositionRepository orderPositionRepository;
    private final OrderRepository orderRepository;
    private final PartRepository partRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderPositionRepository orderPositionRepository,
                            OrderRepository orderRepository,
                            PartRepository partRepository,
                            UserService userService) {
        this.orderPositionRepository = orderPositionRepository;
        this.orderRepository = orderRepository;
        this.partRepository = partRepository;
        this.userService = userService;
    }

    @Override
    public List<OrderEntity> getAllForUserById(Long userId) {
        return orderRepository.getAllByOwner_Id(userId, Sort.by("createDate").descending());
    }

    @Override
    public Optional<OrderEntity> create(OrderCreateRequest orderCreateRequest) throws CredentialNotFoundException {
        Set<OrderPositionEntity> orderPositionEntities;
        try{
            orderPositionEntities = orderCreateRequest.getOrderPositions()
                    .stream()
                    .map(position -> {
                        OrderPositionEntity pos = new OrderPositionEntity();
                        PartEntity part = partRepository.getById(position.getPartId());
                        if(part == null){
                            throw new OrderException(String.format("No order found with id = `%d`", pos.getId()));
                        }
                        pos.setPart(part);
                        pos.setAmount(position.getAmount());
                        return pos;
                    }).collect(Collectors.toSet());
        } catch (OrderException e){
            return Optional.empty();
        }

        orderPositionEntities.forEach(orderPositionRepository::save);
        UserEntity owner = userService.getCurrent().orElseThrow(() -> new CredentialNotFoundException("Unauthorized"));
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOwner(owner);
        orderEntity.setCreateDate(new Date());
        orderEntity.setApproved(false);
        orderEntity.setDeliveryDate(orderCreateRequest.getDeliveryDate());
        orderEntity.setOrderPositions(orderPositionEntities);
        owner.addOrder(orderEntity);
        orderRepository.save(orderEntity);
        return Optional.of(orderEntity);
    }
}
