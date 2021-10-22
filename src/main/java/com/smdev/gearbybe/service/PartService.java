package com.smdev.gearbybe.service;

import com.smdev.gearbybe.model.dto.PartCreateRequest;
import com.smdev.gearbybe.model.dto.PartReserveRequest;
import com.smdev.gearbybe.model.dto.PartSearchRequest;
import com.smdev.gearbybe.model.entity.PartEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PartService {
    List<PartEntity> getAll();
    Optional<PartEntity> updateAmountOfParts(PartReserveRequest partReserveRequest);
    Optional<PartEntity> create(PartCreateRequest partCreateRequest);
    List<PartEntity> search(PartSearchRequest partSearchRequest);
}
