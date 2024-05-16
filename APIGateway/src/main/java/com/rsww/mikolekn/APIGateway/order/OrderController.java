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

    @PostMapping("/orders")
    public ResponseEntity<GetAllOrdersResponse> getOrders(@RequestBody OrdersDto ordersDto) {
        return orderService.getOrders(ordersDto);
    }

}
