package com.rsww.mikolekn.APIGateway.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    public ResponseEntity<OrderResponse> orderTrip(OrderDto orderDto) {
        return new ResponseEntity<>(new OrderResponse(UUID.randomUUID() ,true , "1"), HttpStatus.OK);
    }

    public ResponseEntity<OrdersResponse> getOrders(OrdersDto ordersDto) {
        return new ResponseEntity<>(new OrdersResponse(), HttpStatus.OK);
    }
}
