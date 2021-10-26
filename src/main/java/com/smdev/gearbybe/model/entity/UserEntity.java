package com.smdev.gearbybe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private double cash;
    private String address;
    private UserRole role;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<OrderEntity> orders;

}
