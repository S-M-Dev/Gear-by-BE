package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class PartReserveRequest {
    @JsonProperty(required = true)
    @Min(value = 1, message = "Part id cannot be less than one")
    private Long id;
    @JsonProperty(required = true)
    @Min(value = 1, message = "Count of reserved parts cannot be less than one")
    private int reserve;
}
