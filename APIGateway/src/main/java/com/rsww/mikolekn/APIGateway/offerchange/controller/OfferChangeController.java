package com.rsww.mikolekn.APIGateway.offerchange.controller;

import com.rsww.mikolekn.APIGateway.offerchange.dto.OfferChangeEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightPriceChangedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.FlightRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.HotelRemovedEvent;
import com.rsww.mikolekn.APIGateway.offerchange.model.RoomPriceChangeEvent;
import com.rsww.mikolekn.APIGateway.offerchange.service.OfferChangeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Controller
public class OfferChangeController {
    private final OfferChangeService offerChangeService;

    public OfferChangeController(OfferChangeService offerChangeService) {
        this.offerChangeService = offerChangeService;
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.HotelRemovedEventFrontQueue}")
    public void hotelRemovedEventHandler(HotelRemovedEvent event) {
        offerChangeService.notifyHotelRemovedEvent(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.FlightRemovedEventFrontQueue}")
    public void flightRemovedEventHandler(FlightRemovedEvent event) {
        offerChangeService.notifyFlightRemovedEvent(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.RoomPriceChangedEventFrontQueue}")
    public void roomPriceChangeEventHandler(RoomPriceChangeEvent event) {
        offerChangeService.notifyRoomPriceChangedEvent(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.FlightPriceChangedEventFrontQueue}")
    public void flightPriceChangedEventHandler(FlightPriceChangedEvent event) {
        offerChangeService.notifyFlightPriceChangedEvent(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.OfferChangeFrontQueue}")
    public void offerChangeEventHandler(OfferChangeEvent event) {
        offerChangeService.notifyOfferChangeEvent(event);
    }
}
