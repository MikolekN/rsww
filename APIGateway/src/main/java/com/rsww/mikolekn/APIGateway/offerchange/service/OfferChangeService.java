package com.rsww.mikolekn.APIGateway.offerchange.service;

import com.rsww.mikolekn.APIGateway.offer.OfferService;
import com.rsww.mikolekn.APIGateway.offer.dto.GetAllOffersRequest;
import com.rsww.mikolekn.APIGateway.offer.dto.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offer.dto.OffersDto;
import com.rsww.mikolekn.APIGateway.offerchange.dto.GetLastChangesResponse;
import com.rsww.mikolekn.APIGateway.offerchange.dto.GetOfferChangesRequest;
import com.rsww.mikolekn.APIGateway.offerchange.dto.GetOfferChangesResponse;
import com.rsww.mikolekn.APIGateway.offerchange.dto.OfferChangeEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightPriceChangedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.HotelRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.RoomPriceChangeEvent;
import com.rsww.mikolekn.APIGateway.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;


@Service
public class OfferChangeService {
    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    static Logger logger = LoggerFactory.getLogger(OfferChangeService.class);
    private final Queue getLastOfferChangesQueue;
    public OfferChangeService(RabbitTemplate rabbitTemplate, SimpMessagingTemplate messagingTemplate, Queue getLastOfferChangesQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingTemplate = messagingTemplate;
        this.getLastOfferChangesQueue = getLastOfferChangesQueue;
    }

    public ResponseEntity<GetLastChangesResponse> getOfferChanges() {
        logger.info("Received GetOfferChangesRequest");
        rabbitTemplate.setReplyTimeout(15000);

        GetOfferChangesResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                getLastOfferChangesQueue.getName(),
                new GetOfferChangesRequest(UUID.randomUUID()),
                new ParameterizedTypeReference<>() {});
        return prepareResponse(new GetLastChangesResponse(response.getOfferChangeEvents()));
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
        messagingTemplate.convertAndSend("/topic/offer-change-event", event);
    }
}
