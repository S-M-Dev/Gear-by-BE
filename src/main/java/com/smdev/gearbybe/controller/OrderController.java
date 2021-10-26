package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.dto.response.OrderResponse;
import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST,
        RequestMethod.OPTIONS,
        RequestMethod.GET,
        RequestMethod.PUT,
        RequestMethod.DELETE})
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest orderCreateRequest){
        Optional<OrderEntity> orderEntity = orderService.create(orderCreateRequest);
        if(orderEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderEntity.map(OrderResponse::new).get());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getForCurrent(){
        List<OrderEntity> allForUserById = orderService.getAllForCurrent();
        if(allForUserById.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allForUserById
                .stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancel(@PathVariable Long id){
        if(orderService.cancel(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody OrderCreateRequest orderCreateRequest){
        Optional<OrderEntity> result = orderService.updateOrder(id, orderCreateRequest);
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result.map(OrderResponse::new).get());
    }

}
