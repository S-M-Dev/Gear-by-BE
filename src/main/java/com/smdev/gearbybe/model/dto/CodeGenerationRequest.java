package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class CodeGenerationRequest {
    @Email(message = "Invalid email format")
    @JsonProperty(required = true)
    private String email;
}
