package com.smdev.gearbybe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserDataInput {
    @JsonProperty(required = true)
    private String fullName;
    @JsonProperty(required = true)
    private String address;
    @Pattern(regexp = "(^\\+[0-9]{6,20}$)", message = "Invalid Phone Number")
    @JsonProperty(required = true)
    private String phoneNumber;
}
