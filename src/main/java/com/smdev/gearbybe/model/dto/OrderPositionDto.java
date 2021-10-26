package com.smdev.gearbybe.model.dto;

import com.smdev.gearbybe.model.entity.OrderPositionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderPositionDto {
    private Long partId;
    private int amount;

    public OrderPositionDto(OrderPositionEntity orderPositionEntity){
        this.partId = orderPositionEntity.getPart().getId();
        this.amount = orderPositionEntity.getAmount();
    }

}
