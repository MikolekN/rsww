package com.rsww.mikolekn.APIGateway.order;

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

    @GetMapping("/orders")
    public ResponseEntity<OrdersResponse> getOrders(@RequestBody OrdersDto ordersDto) {
        return orderService.getOrders(ordersDto);
    }

}
