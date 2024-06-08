package com.rsww.mikolekn.APIGateway.offerchange.controller;

import com.rsww.mikolekn.APIGateway.offer.dto.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offerchange.dto.GetLastChangesResponse;
import com.rsww.mikolekn.APIGateway.offerchange.dto.GetOfferChangesResponse;
import com.rsww.mikolekn.APIGateway.offerchange.service.OfferChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
