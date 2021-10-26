package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.mapper.PartMapper;
import com.smdev.gearbybe.model.dto.PartCreateRequest;
import com.smdev.gearbybe.model.dto.PartReserveRequest;
import com.smdev.gearbybe.model.dto.PartSearchRequest;
import com.smdev.gearbybe.model.entity.PartEntity;
import com.smdev.gearbybe.repository.PartRepository;
import com.smdev.gearbybe.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<PartEntity> getAll() {
        return partRepository.findAll();
    }

    @Override
    public Optional<PartEntity> updateAmountOfParts(PartReserveRequest partReserveRequest) {
        Optional<PartEntity> partEntityOptional = partRepository.findById(partReserveRequest.getId());

        if(partEntityOptional.isPresent()){
            PartEntity partEntity = partEntityOptional.get();
            partEntity.setAmount(partEntity.getAmount() - partReserveRequest.getReserve());
            partRepository.save(partEntity);
            partEntityOptional = Optional.of(partEntity);
        }

        return partEntityOptional;
    }

    @Override
    public Optional<PartEntity> create(PartCreateRequest partCreateRequest) {
        if(partRepository.findByName(partCreateRequest.getName()).isPresent()){
            return Optional.empty();
        }

        PartEntity partEntity = PartMapper.createToPart(partCreateRequest);
        partEntity = partRepository.save(partEntity);
        return Optional.of(partEntity);
    }

    @Override
    public List<PartEntity> search(PartSearchRequest partSearchRequest) {
        PartEntity probe = PartMapper.searchToPart(partSearchRequest);
        ExampleMatcher exampleMatcher = partSearchRequest.toMatcher();

        if(Objects.isNull(exampleMatcher)){
            return getAll();
        }

        Example<PartEntity> partEntityExample = Example.of(probe, exampleMatcher);
        return partRepository.findAll(partEntityExample);
    }
}
