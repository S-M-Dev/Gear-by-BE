package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty
    @Email
    @JsonProperty(required = true)
    private String email;
    @NotEmpty
    @Pattern(regexp = "(^(\\w|_!\\.){6,20}$)", message = "Invalid Password")
    @JsonProperty(required = true)
    private String password;
}
