package com.smdev.gearbybe.model.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
public class OrderCreateRequest {
    @NotEmpty(message = "List of positions cannot be empty")
    @Valid
    private List<OrderPositionDto> orderPositions;
    private Date deliveryDate;
    private String address;
}
