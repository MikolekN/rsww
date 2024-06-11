package com.rsww.mikolekn.APIGateway.offerchange.controller;

import com.rsww.mikolekn.APIGateway.offer.dto.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offerchange.dto.*;
import com.rsww.mikolekn.APIGateway.offerchange.service.OfferChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OfferChangeRestController {
    private final OfferChangeService offerChangeService;

    @Autowired
    public OfferChangeRestController(OfferChangeService offerChangeService) {
        this.offerChangeService = offerChangeService;
    }

    @PostMapping("/offer-changes")
    public ResponseEntity<GetLastChangesResponse> offers() {
        return offerChangeService.getOfferChanges();
    }

    @PostMapping("/offer-changes/change-flight-price")
    public void changeFlightPrice(@RequestBody ChangeFlightPriceCommand command) {
        offerChangeService.generateChangeFlightPriceCommand(command);
    }
    @PostMapping("/offer-changes/change-room-price")
    public void changeRoomPrice(@RequestBody ChangeRoomPriceCommand command) {
        offerChangeService.generateChangeRoomPriceCommand(command);
    }
    @PostMapping("/offer-changes/remove-hotel")
    public void removeHotel(@RequestBody RemoveHotelCommand command) {
        offerChangeService.generateRemoveHotelCommand(command);
    }
    @PostMapping("/offer-changes/remove-flight")
    public void removeFlight(@RequestBody RemoveFlightCommand command) {
        offerChangeService.generateRemoveFlightCommand(command);
    }
}
