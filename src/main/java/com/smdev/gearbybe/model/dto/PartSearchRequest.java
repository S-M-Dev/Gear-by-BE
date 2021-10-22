package com.smdev.gearbybe.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartSearchRequest {
    private String name = "";
    private String carModel = "";
    private String carMark = "";
}
