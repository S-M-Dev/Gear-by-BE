package com.smdev.gearbybe.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createDate;
    private Date deliveryDate;
    private boolean approved;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity owner;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<OrderPositionEntity> orderPositions;

    public void addOrderPosition(OrderPositionEntity orderPositionEntity){
        orderPositions.add(orderPositionEntity);
    }

    public void removeOrderPosition(OrderPositionEntity orderPositionEntity){
        orderPositions.remove(orderPositionEntity);
    }
}
