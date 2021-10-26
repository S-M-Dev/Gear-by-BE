package com.smdev.gearbybe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<OrderEntity> orders = new HashSet<>();

    public void addOrder(OrderEntity orderEntity){
        orders.add(orderEntity);
    }

    public void removeOrder(OrderEntity orderEntity){
        orders.remove(orderEntity);
    }

}
