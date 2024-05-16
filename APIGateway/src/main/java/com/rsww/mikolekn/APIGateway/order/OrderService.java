package com.rsww.mikolekn.APIGateway.order;

import com.rsww.mikolekn.APIGateway.payment.PaymentRequest;
import com.rsww.mikolekn.APIGateway.payment.PaymentResponse;
import com.rsww.mikolekn.APIGateway.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import com.rsww.mikolekn.APIGateway.offer.DTO.GetAllOffersRequest;
import com.rsww.mikolekn.APIGateway.offer.DTO.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offer.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
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

    @Autowired
    OrderService(RabbitTemplate rabbitTemplate, Queue reserveTrip) {
        this.rabbitTemplate = rabbitTemplate;
        this.reserveTrip = reserveTrip;
    }

    private final RabbitTemplate rabbitTemplate;
    private final Queue ordersQueue;

    static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate, Queue ordersQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.ordersQueue = ordersQueue;
    }

    public ResponseEntity<OrderResponse> orderTrip(OrderDto orderDto) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started an order request with uuid: {}", requestNumber, uuid);

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
        logger.info("{} Received a payment response: {}", requestNumber, orderResponse);

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
}
