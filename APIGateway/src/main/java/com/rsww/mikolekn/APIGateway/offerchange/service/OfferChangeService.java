package com.rsww.mikolekn.APIGateway.offerchange.service;


import com.rsww.mikolekn.APIGateway.offerchange.dto.*;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightPriceChangedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.HotelRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.RoomPriceChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;


@Service
public class OfferChangeService {
    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    static Logger logger = LoggerFactory.getLogger(OfferChangeService.class);
    private final Queue getLastOfferChangesQueue;

    private final Queue generateFlightPriceChangeQueue;
    private final Queue generateRoomPriceChangeQueue;
    private final Queue generateFlightRemoveQueue;
    private final Queue generateHotelRemoveQueue;
    public OfferChangeService(RabbitTemplate rabbitTemplate, SimpMessagingTemplate messagingTemplate, Queue getLastOfferChangesQueue, Queue generateFlightPriceChangeQueue, Queue generateRoomPriceChangeQueue, Queue generateFlightRemoveQueue, Queue generateHotelRemoveQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingTemplate = messagingTemplate;
        this.getLastOfferChangesQueue = getLastOfferChangesQueue;
        this.generateFlightPriceChangeQueue = generateFlightPriceChangeQueue;
        this.generateRoomPriceChangeQueue = generateRoomPriceChangeQueue;
        this.generateFlightRemoveQueue = generateFlightRemoveQueue;
        this.generateHotelRemoveQueue = generateHotelRemoveQueue;
    }

    public ResponseEntity<GetLastChangesResponse> getOfferChanges() {
        logger.info("Received GetOfferChangesRequest");
        rabbitTemplate.setReplyTimeout(15000);

        GetOfferChangesResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getLastOfferChangesQueue.getName(),
                new GetOfferChangesRequest(UUID.randomUUID()),
                new ParameterizedTypeReference<>() {});
        GetLastChangesResponse changesResponse = new GetLastChangesResponse(response.getOfferChangeEvents());
        if (response.getOfferChangeEvents() != null)
            changesResponse.setResponse(true);
        return prepareResponse(changesResponse);
    }

    public void notifyHotelRemovedEvent(HotelRemovedEvent event) {
        messagingTemplate.convertAndSend("/topic/hotel-removed-event", event);
    }

    public void notifyFlightRemovedEvent(FlightRemovedEvent event) {
        messagingTemplate.convertAndSend("/topic/flight-removed-event", event);
    }

    public void notifyRoomPriceChangedEvent(RoomPriceChangeEvent event) {
        messagingTemplate.convertAndSend("/topic/room-price-changed-event", event);
    }

    public void notifyFlightPriceChangedEvent(FlightPriceChangedEvent event) {
        messagingTemplate.convertAndSend("/topic/flight-price-changed-event", event);
    }

    public void notifyOfferChangeEvent(OfferChangeEvent event) {
        rabbitTemplate.setReplyTimeout(15000);

        GetOfferChangesResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getLastOfferChangesQueue.getName(),
                new GetOfferChangesRequest(UUID.randomUUID()),
                new ParameterizedTypeReference<>() {});
        if (response != null)
            messagingTemplate.convertAndSend("/topic/offer-change-event", response);
    }

    public void generateChangeFlightPriceCommand(ChangeFlightPriceCommand command) {
        rabbitTemplate.convertAndSend(generateFlightPriceChangeQueue.getName(), command);
    }

    public void generateChangeRoomPriceCommand(ChangeRoomPriceCommand command) {
        rabbitTemplate.convertAndSend(generateRoomPriceChangeQueue.getName(), command);
    }

    public void generateRemoveHotelCommand(RemoveHotelCommand command) {
        rabbitTemplate.convertAndSend(generateHotelRemoveQueue.getName(), command);
    }

    public void generateRemoveFlightCommand(RemoveFlightCommand command) {
        rabbitTemplate.convertAndSend(generateFlightRemoveQueue.getName(), command);
    }
}
