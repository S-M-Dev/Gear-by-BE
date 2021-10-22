package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.PartCreateRequest;
import com.smdev.gearbybe.model.dto.PartReserveRequest;
import com.smdev.gearbybe.model.dto.PartSearchRequest;
import com.smdev.gearbybe.model.entity.PartEntity;
import com.smdev.gearbybe.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/part")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartEntity>> getAll(){
        List<PartEntity> parts = partService.getAll();
        if(parts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parts);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartEntity>> search(@Valid @RequestBody PartSearchRequest partSearchRequest){
        List<PartEntity> parts = partService.search(partSearchRequest);
        if(parts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parts);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartEntity> create(@Valid @RequestBody PartCreateRequest partCreateRequest){
        Optional<PartEntity> partEntity = partService.create(partCreateRequest);
        if(partEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(partEntity.get());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartEntity> updateAmount(@Valid @RequestBody PartReserveRequest partReserveRequest){
        Optional<PartEntity> partEntity = partService.updateAmountOfParts(partReserveRequest);
        if(partEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partEntity.get());
    }

}
