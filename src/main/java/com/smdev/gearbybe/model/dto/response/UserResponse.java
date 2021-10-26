package com.smdev.gearbybe.model.dto.response;

import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.model.entity.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private double cash;
    private String address;
    private UserRole role;

    public UserResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.fullName = userEntity.getFullName();
        this.phoneNumber = userEntity.getPhoneNumber();
        this.cash = userEntity.getCash();
        this.address = userEntity.getAddress();
        this.role = userEntity.getRole();
    }
}
