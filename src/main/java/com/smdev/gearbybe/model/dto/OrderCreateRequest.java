package com.smdev.gearbybe.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderCreateRequest {
    private List<OrderPositionDto> orderPositions;
    private Date deliveryDate;
}
