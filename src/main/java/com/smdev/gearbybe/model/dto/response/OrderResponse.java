package com.smdev.gearbybe.model.dto.response;

import com.smdev.gearbybe.model.dto.OrderPositionDto;
import com.smdev.gearbybe.model.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private int summary;
    private Date createDate;
    private Date deliveryDate;
    private UserResponse owner;
    private List<OrderPositionDto> positions;

    public OrderResponse(OrderEntity orderEntity){
        orderId = orderEntity.getId();
        createDate = orderEntity.getCreateDate();
        deliveryDate = orderEntity.getDeliveryDate();
        owner = new UserResponse(orderEntity.getOwner());
        summary = orderEntity.getOrderPositions()
                .stream()
                .mapToInt(p -> p.getAmount() * p.getPart().getPrice())
                .sum();
        positions = orderEntity.getOrderPositions()
                .stream()
                .map(OrderPositionDto::new)
                .collect(Collectors.toList());
    }

}
