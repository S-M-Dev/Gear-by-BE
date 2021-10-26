package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.DonateRequest;
import com.smdev.gearbybe.model.dto.response.OrderResponse;
import com.smdev.gearbybe.model.dto.response.UserResponse;
import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.model.entity.UserEntity;
import com.smdev.gearbybe.service.PaymentService;
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
import java.util.Optional;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Pay for order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payed successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "202", description = "Not enough cash",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No order found for current user")
    })
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id){
        Optional<OrderEntity> entity = paymentService.pay(id);
        if(entity.isEmpty()){
            return ResponseEntity.notFound().build();
        }else if(entity.get().isApproved()){
            return ResponseEntity.ok(entity.map(OrderResponse::new).get());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entity.map(OrderResponse::new).get());
    }

    @Operation(summary = "Donate money")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> donate(@Valid @RequestBody DonateRequest donateRequest){
        UserEntity user = paymentService.donate(donateRequest.getCash());
        UserResponse response = new UserResponse(user);
        return ResponseEntity.ok(response);
    }
}
