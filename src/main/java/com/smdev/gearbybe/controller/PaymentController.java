package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.response.OrderResponse;
import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
