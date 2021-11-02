package com.smdev.gearbybe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.ExampleMatcher;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.caseSensitive;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartSearchRequest {
    private String name = "";
    private String carModel = "";
    private String carMark = "";

    public ExampleMatcher toMatcher(){
        if(name.isEmpty() && carMark.isEmpty() && carModel.isEmpty()){
            return null;
        }

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "description", "amount", "price");

        if(!name.isEmpty()) {
            exampleMatcher = exampleMatcher.withMatcher("name", contains());
        }

        if(!carModel.isEmpty()) {
            exampleMatcher = exampleMatcher.withMatcher("carModel", caseSensitive());
        }

        if(!carMark.isEmpty()) {
            exampleMatcher = exampleMatcher.withMatcher("carMark", caseSensitive());
        }

        return exampleMatcher;
    }

}
