package com.smdev.gearbybe.repository;

import com.smdev.gearbybe.model.entity.OrderPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPositionEntity, Long> {
}
