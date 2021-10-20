package com.smdev.gearbybe.mapper;

import com.smdev.gearbybe.model.dto.RegistrationRequest;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.model.entity.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static UserEntity registrationToUser(RegistrationRequest registrationRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registrationRequest.getEmail());
        userEntity.setPassword(registrationRequest.getPassword());
        userEntity.setPhoneNumber(registrationRequest.getPhoneNumber());
        userEntity.setFullName(registrationRequest.getFullName());
        userEntity.setCash(0);
        userEntity.setAddress("");
        userEntity.setRole(UserRole.ROLE_USER);
        return userEntity;
    }

}
