package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.PartCreateRequest;
import com.smdev.gearbybe.model.dto.PartUpdateRequest;
import com.smdev.gearbybe.model.dto.PartSearchRequest;
import com.smdev.gearbybe.model.entity.PartEntity;
import com.smdev.gearbybe.service.PartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all parts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PartEntity.class))}),
            @ApiResponse(responseCode = "204", description = "No parts found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartEntity>> getAll(){
        List<PartEntity> parts = partService.getAll();
        if(parts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parts);
    }

    @Operation(summary = "Search parts by optional parameters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PartEntity.class))}),
            @ApiResponse(responseCode = "204", description = "No parts found")
    })
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartEntity>> search(@Valid @RequestBody PartSearchRequest partSearchRequest){
        List<PartEntity> parts = partService.search(partSearchRequest);
        if(parts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parts);
    }

    @Operation(summary = "Create a new part")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PartEntity.class))}),
            @ApiResponse(responseCode = "409", description = "Part with same name is already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartEntity> create(@Valid @RequestBody PartCreateRequest partCreateRequest){
        Optional<PartEntity> partEntity = partService.create(partCreateRequest);
        if(partEntity.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(partEntity.get());
    }

    @Operation(summary = "Update part amount")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PartEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartEntity> updateAmount(@Valid @RequestBody PartUpdateRequest partReserveRequest){
        Optional<PartEntity> partEntity = partService.updateAmountOfParts(partReserveRequest);
        if(partEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partEntity.get());
    }

}
