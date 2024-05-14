package com.rsww.mikolekn.APIGateway.offer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OfferController {
    private final OfferService offerService;

    OfferController (OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/offers")
    public ResponseEntity<OffersResponse> offers(OffersDto offersDto) {
        return offerService.offers(offersDto);
    }

    @GetMapping("/offer")
    public ResponseEntity<OfferResponse> offer(OfferDto offerDto) {
        return offerService.offer(offerDto);
    }
}
