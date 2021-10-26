package com.smdev.gearbybe.mapper;

import com.smdev.gearbybe.model.dto.PartCreateRequest;
import com.smdev.gearbybe.model.dto.PartSearchRequest;
import com.smdev.gearbybe.model.entity.PartEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartMapper {

    public static PartEntity createToPart(PartCreateRequest partCreateRequest){
        PartEntity result = new PartEntity();
        result.setAmount(partCreateRequest.getAmount());
        result.setCarMark(partCreateRequest.getCarMark());
        result.setCarModel(partCreateRequest.getCarModel());
        result.setDescription(partCreateRequest.getDescription());
        result.setName(partCreateRequest.getName());
        result.setPrice(partCreateRequest.getPrice());
        return result;
    }

    public static PartEntity searchToPart(PartSearchRequest partSearchRequest){
        PartEntity result = new PartEntity();
        result.setCarMark(partSearchRequest.getCarMark());
        result.setCarModel(partSearchRequest.getCarModel());
        result.setName(partSearchRequest.getName());
        return result;
    }

}
