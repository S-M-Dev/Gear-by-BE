package com.smdev.gearbybe.model.dto;

import com.smdev.gearbybe.model.entity.OrderPositionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class OrderPositionDto {
    @Min(value = 1, message = "Part id cannot be less than one")
    private Long partId;
    @Min(value = 1, message = "Amount cannot be less than one")
    private int amount;

    public OrderPositionDto(OrderPositionEntity orderPositionEntity){
        this.partId = orderPositionEntity.getPart().getId();
        this.amount = orderPositionEntity.getAmount();
    }

}
