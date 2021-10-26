package com.smdev.gearbybe.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderPositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private PartEntity part;
    private int amount;
}
