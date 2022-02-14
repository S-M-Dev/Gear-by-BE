package com.smdev.gearbybe.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createDate;
    private Date deliveryDate;
    private boolean approved;
    private String address;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity owner;
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderPositionEntity> orderPositions = new LinkedList<>();

    public void addOrderPosition(OrderPositionEntity orderPositionEntity){
        orderPositions.add(orderPositionEntity);
    }

    public void removeOrderPosition(OrderPositionEntity orderPositionEntity){
        orderPositions.remove(orderPositionEntity);
    }
}
