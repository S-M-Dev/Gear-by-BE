package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class PartCreateRequest {
    @JsonProperty(required = true)
    @NotEmpty(message = "Part name is empty")
    private String name;
    @JsonProperty(required = true)
    @NotEmpty(message = "Part description is empty")
    private String description;
    @JsonProperty(required = true)
    @NotEmpty(message = "Car model is empty")
    private String carModel;
    @JsonProperty(required = true)
    @NotEmpty(message = "Car mark is empty")
    private String carMark;
    @JsonProperty(required = true)
    @Min(value = 1, message = "Amount of parts cannot be less then one")
    private int amount;
    @JsonProperty(required = true)
    @Min(value = 1, message = "Price cannot be less then one")
    private int price;
}
