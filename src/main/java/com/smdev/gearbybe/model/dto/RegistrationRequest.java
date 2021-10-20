package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class RegistrationRequest {
    @NotEmpty
    @Email
    @JsonProperty(required = true)
    private String email;
    @NotEmpty
    @Pattern(regexp = "(^([a-zA-Z]|\\s){2,30}$)", message = "Invalid Full Name")
    @JsonProperty(required = true)
    private String fullName;
    @NotEmpty
    @Pattern(regexp = "(^\\+[0-9]{6,20}$)", message = "Invalid Phone Number")
    @JsonProperty(required = true)
    private String phoneNumber;
    @NotEmpty
    @Pattern(regexp = "(^(\\w|_!\\.){6,20}$)", message = "Invalid Password")
    @JsonProperty(required = true)
    private String password;
}
