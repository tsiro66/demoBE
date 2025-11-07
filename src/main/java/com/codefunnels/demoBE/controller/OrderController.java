package com.codefunnels.demoBE.controller;

import com.codefunnels.demoBE.dto.OrderRequest;
import com.codefunnels.demoBE.model.Order;
import com.codefunnels.demoBE.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Delegate all business logic to the service layer
            Order placedOrder = orderService.placeOrder(orderRequest);
            return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Catch specific errors (like insufficient stock) and return a client error status
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}