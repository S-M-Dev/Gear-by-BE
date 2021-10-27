package com.smdev.gearbybe.repository;

import com.smdev.gearbybe.model.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
    Optional<CodeEntity> findByUserEntity_Email(String email);
}
