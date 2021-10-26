package com.smdev.gearbybe.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    private String password;
    private String phoneNumber;
    private double cash;
    private String address;
    private UserRole role;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<OrderEntity> orders = new LinkedList<>();

    public void addOrder(OrderEntity orderEntity){
        orders.add(orderEntity);
    }

    public void removeOrder(OrderEntity orderEntity){
        orders.remove(orderEntity);
    }

}
