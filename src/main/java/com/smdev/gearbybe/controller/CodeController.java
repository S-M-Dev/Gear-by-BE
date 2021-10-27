package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.CodeGenerationRequest;
import com.smdev.gearbybe.model.dto.CodeValidationRequest;
import com.smdev.gearbybe.service.CodeService;
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

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/code")
public class CodeController {

    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @Operation(summary = "Generate recovery code for user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully generated"),
            @ApiResponse(responseCode = "404", description = "User with same email not found")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generate(@Valid @RequestBody CodeGenerationRequest codeGenerationRequest){
        boolean result = codeService.generate(codeGenerationRequest.getEmail());
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Validate recovery code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully generated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))}),
            @ApiResponse(responseCode = "404", description = "User with same email not found or code is invalid")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validate(@Valid @RequestBody CodeValidationRequest codeValidationRequest){
        Long id = codeService.activate(codeValidationRequest.getEmail(), codeValidationRequest.getCode());
        if(id == -1){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(id);
    }

}
