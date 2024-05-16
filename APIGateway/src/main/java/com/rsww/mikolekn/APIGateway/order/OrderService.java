package com.rsww.mikolekn.APIGateway.order;

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

import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;

@Service
public class OrderService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue ordersQueue;

    static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate, Queue ordersQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.ordersQueue = ordersQueue;
    }

    public ResponseEntity<OrderResponse> orderTrip(OrderDto orderDto) {
        return new ResponseEntity<>(new OrderResponse(UUID.randomUUID() ,true , "1"), HttpStatus.OK);
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
