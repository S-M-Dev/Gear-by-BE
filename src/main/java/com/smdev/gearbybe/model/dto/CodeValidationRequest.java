package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class CodeValidationRequest {
    @Email(message = "Invalid email format")
    @JsonProperty(required = true)
    private String email;
    @Pattern(regexp = "(^([0-9]{3}-[A-Z]{4}-[0-9]{3})$)", message = "Invalid code format")
    @JsonProperty(required = true)
    private String code;
}
