package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PasswordResetRequest {
    @JsonProperty(required = true)
    @Min(value = 1, message = "Invalid user id")
    private Long id;
    @Pattern(regexp = "(^(\\w|_!\\.){6,20}$)", message = "Invalid Password")
    @JsonProperty(required = true)
    private String password;
}
