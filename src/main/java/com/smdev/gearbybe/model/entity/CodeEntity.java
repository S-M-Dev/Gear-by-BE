package com.smdev.gearbybe.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;
    private String code;
}
