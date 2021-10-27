package com.smdev.gearbybe.controller;

import com.smdev.gearbybe.model.dto.OrderCreateRequest;
import com.smdev.gearbybe.model.dto.response.OrderResponse;
import com.smdev.gearbybe.model.entity.OrderEntity;
import com.smdev.gearbybe.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Parts in order not found")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest orderCreateRequest){
        Optional<OrderEntity> orderEntity = orderService.create(orderCreateRequest);
        if(orderEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderEntity.map(OrderResponse::new).get());
    }

    @Operation(summary = "Get all orders for currently authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "204", description = "No orders found")
    })
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

    @Operation(summary = "Cancel order by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Canceled successfully"),
            @ApiResponse(responseCode = "404", description = "No order found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity cancel(@PathVariable Long id){
        if(orderService.cancel(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No order found")
    })
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
