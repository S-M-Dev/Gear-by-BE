package com.smdev.gearbybe.repository;

import com.smdev.gearbybe.model.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<PartEntity, Long> {
    Optional<PartEntity> findByName(String name);
    List<PartEntity> findAllByNameLikeAndCarModelAndCarMark(String name, String carModel, String carMark);
}
