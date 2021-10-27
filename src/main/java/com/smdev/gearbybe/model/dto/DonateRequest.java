package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DonateRequest {
    @JsonProperty(required = true)
    @Min(value = 1, message = "Donate value cannot be less than one")
    private int cash;
}
