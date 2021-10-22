package com.smdev.gearbybe.repository;

import com.smdev.gearbybe.model.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<PartEntity, Long> {
    List<PartEntity> findAllByNameLikeAndCarModelAndCarMark(String name, String carModel, String carMark);
}
