package com.rsww.mikolekn.APIGateway.order.controller;

import com.rsww.mikolekn.APIGateway.order.dto.*;
import com.rsww.mikolekn.APIGateway.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> orderTrip(@RequestBody OrderDto orderDto) {
        return orderService.orderTrip(orderDto);
    }

    @PostMapping("/order/{id}")
    public ResponseEntity<OrderInfoResponse> orderTrip(@PathVariable String id) {
        ResponseEntity<OrderInfoResponse> responseEntity = orderService.getOrderInfo(id);
        OrderInfoResponse response = responseEntity.getBody();
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return responseEntity;

    }

    @PostMapping("/orders")
    public ResponseEntity<GetAllOrdersResponse> getOrders(@RequestBody OrdersDto ordersDto) {
        return orderService.getOrders(ordersDto);
    }

}
