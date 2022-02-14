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
import com.smdev.gearbybe.repository.UserRepository;
import com.smdev.gearbybe.service.OrderService;
import com.smdev.gearbybe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderPositionRepository orderPositionRepository;
    private final OrderRepository orderRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderPositionRepository orderPositionRepository,
                            OrderRepository orderRepository,
                            PartRepository partRepository,
                            UserService userService,
                            UserRepository userRepository) {
        this.orderPositionRepository = orderPositionRepository;
        this.orderRepository = orderRepository;
        this.partRepository = partRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderEntity> getAllForUserById(Long userId) {
        return orderRepository.getAllByOwner_Id(userId, Sort.by("createDate").descending());
    }

    @Override
    public Optional<OrderEntity> create(OrderCreateRequest orderCreateRequest) {
        List<OrderPositionEntity> orderPositionEntities;
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
                    }).collect(Collectors.toList());
        } catch (OrderException e){
            return Optional.empty();
        }

        orderPositionEntities.forEach(orderPositionRepository::save);
        UserEntity owner = userService.getCurrent().get();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOwner(owner);
        orderEntity.setCreateDate(new Date());
        orderEntity.setApproved(false);
        orderEntity.setDeliveryDate(orderCreateRequest.getDeliveryDate());
        orderEntity.setOrderPositions(orderPositionEntities);
        orderEntity.setAddress(orderCreateRequest.getAddress());
        owner.addOrder(orderEntity);
        orderRepository.save(orderEntity);
        return Optional.of(orderEntity);
    }

    @Override
    public List<OrderEntity> getAllForCurrent() {
        Long userId = userService.getCurrent().get().getId();
        return getAllForUserById(userId);
    }

    @Override
    public boolean cancel(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        UserEntity user = userService.getCurrent().get();

        if(orderEntity.isEmpty() || orderEntity.get().getOwner().getId() != user.getId()){
            return false;
        }

        OrderEntity order = orderEntity.get();
        if(!order.isApproved()){
            user.removeOrder(order);
            userRepository.save(user);
            List<OrderPositionEntity> orderPositions = order.getOrderPositions();
            for(OrderPositionEntity o : orderPositions){
                o.setPart(null);
                order.removeOrderPosition(o);
                orderPositionRepository.delete(o);
            }
            order.setOwner(null);
            orderRepository.delete(order);
        }

        return true;
    }

    @Override
    public Optional<OrderEntity> updateOrder(Long id, OrderCreateRequest orderCreateRequest) {
        if(cancel(id)){
           return create(orderCreateRequest);
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrderEntity> approve(Long id) {
        UserEntity current = userService.getCurrent().get();
        Optional<OrderEntity> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty() || orderOptional.get().getOwner().getId() != current.getId()){
            return Optional.empty();
        }

        OrderEntity order = orderOptional.get();
        order.setApproved(true);
        order.getOrderPositions().forEach(pos -> {
            int amount = pos.getAmount();
            PartEntity part = pos.getPart();
            part.setAmount(part.getAmount() - amount);
            partRepository.save(part);
        });
        orderRepository.save(order);

        return Optional.of(order);
    }
}
