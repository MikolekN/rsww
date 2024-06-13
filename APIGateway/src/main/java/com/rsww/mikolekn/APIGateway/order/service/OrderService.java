package com.rsww.mikolekn.APIGateway.order.service;

import com.rsww.mikolekn.APIGateway.order.dto.*;
import com.rsww.mikolekn.APIGateway.payment.service.PaymentService;
import com.rsww.mikolekn.APIGateway.socket.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;

@Service
public class OrderService {
    private final RabbitTemplate rabbitTemplate;
    static Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final Queue reserveTrip;
    private final Queue ordersQueue;
    private final Queue orderInfoQueue;

    private final SocketService socketService;

    @Autowired
    OrderService(RabbitTemplate rabbitTemplate, Queue reserveTrip, Queue ordersQueue, Queue orderInfoQueue, SocketService socketService) {
        this.rabbitTemplate = rabbitTemplate;
        this.reserveTrip = reserveTrip;
        this.ordersQueue = ordersQueue;
        this.orderInfoQueue = orderInfoQueue;
        this.socketService = socketService;
    }

    public ResponseEntity<OrderResponse> orderTrip(OrderDto orderDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started an order request with uuid: {}", requestNumber, uuid);

        socketService.sendOfferReservedInfo(orderDto.dateFrom(), orderDto.dateTo(), orderDto.hotelUuid());

        OrderResponse orderResponse = rabbitTemplate.convertSendAndReceiveAsType(
                reserveTrip.getName(),
                new OrderRequest(
                        uuid,
                        orderDto.username(),
                        orderDto.flightToUuid(),
                        orderDto.flightFromUuid(),
                        orderDto.hotelUuid(),
                        orderDto.roomType(),
                        orderDto.dateFrom(),
                        orderDto.dateTo(),
                        orderDto.numberOfAdults(),
                        orderDto.numberOfChildrenUnder10(),
                        orderDto.numberOfChildrenUnder18()),
                new ParameterizedTypeReference<>() {});

        logger.info("{} Received an order response: {}", requestNumber, orderResponse);
        socketService.sendUserPreferences(orderDto.username());
        return prepareResponse(orderResponse);
    }

    public ResponseEntity<GetAllOrdersResponse> getOrders(OrdersDto ordersDto) {
        logger.info("Received getOrdersRequest: {}", ordersDto);

        this.rabbitTemplate.setReplyTimeout(10000);
        GetAllOrdersResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                ordersQueue.getName(),
                new GetAllOrdersRequest(ordersDto.username()),
                new ParameterizedTypeReference<>() {});
        if (response != null)
            logger.info("Received GetAllOrdersResponse: {}", response);
        else
            logger.info("GetAllOrdersRequest received a timeout");

        return prepareResponse(response);
    }

    public ResponseEntity<OrderInfoResponse> getOrderInfo(String id) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Received an order info request with uuid: {}", requestNumber, uuid);

        OrderInfoResponse orderInfoResponse = rabbitTemplate.convertSendAndReceiveAsType(
            orderInfoQueue.getName(),
            new GetOrderInfoRequest(uuid, id),
            new ParameterizedTypeReference<>() {});

        logger.info("{} Received a order info response: {}", requestNumber, orderInfoResponse);

        return prepareResponse(orderInfoResponse);
    }
}
