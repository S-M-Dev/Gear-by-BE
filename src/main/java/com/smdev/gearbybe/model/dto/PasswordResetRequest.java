package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PasswordResetRequest {
    @NotEmpty
    @Pattern(regexp = "(^(\\w|_!\\.){6,20}$)", message = "Invalid Password")
    @JsonProperty(required = true)
    private String password;
}
