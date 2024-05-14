package com.rsww.mikolekn.APIGateway.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<GetAllOffersResponse> offers(@RequestBody OffersDto offersDto) {
        return offerService.offers(offersDto);
    }

    @GetMapping("/offer")
    public ResponseEntity<GetOfferInfoResponse> offer(@RequestBody OfferDto offerDto) {
        return offerService.offer(offerDto);
    }
}
