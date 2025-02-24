package com.restaurant.controller;

import com.restaurant.dto.request.OrderRequest;
import com.restaurant.dto.response.ApiResponse;
import com.restaurant.dto.response.OrderResponse;
import com.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class OrderController {

    private final OrderService orderService;


    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request) {
        try {
            OrderResponse response = orderService.createOrder(request);
            ApiResponse<OrderResponse> orderResponse = ApiResponse.ok(response);
            orderResponse.setMessage(String.format("Order created successfully for %s with %d item(s)",
                    response.getCustomerName(),
                    response.getItems().size()));
            orderResponse.setTotalResultCount(1);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse
                            .error(HttpStatus.INTERNAL_SERVER_ERROR, "Error Creating Order : "+ex.getMessage()));
        }
    }

    @GetMapping(value = "/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable String id) {
        try {
            OrderResponse orderResponse = orderService.getOrder(id);
            ApiResponse<OrderResponse> response = ApiResponse.ok(orderResponse);

            response.setMessage(String.format("Retrieved order #%s for %s",
                    id, orderResponse.getCustomerName()));
            response.setTotalResultCount(1);
            return ResponseEntity.ok(ApiResponse.ok(orderResponse));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Error fetching order: " + ex.getMessage()));
        }
    }
}
