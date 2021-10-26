package com.smdev.gearbybe.repository;

import com.smdev.gearbybe.model.entity.OrderEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {
    List<OrderEntity> getAllByOwner_Id(Long ownerId, Sort sort);
}
