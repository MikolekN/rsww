package com.rsww.mikolekn.APIGateway.offer;

import com.rsww.mikolekn.APIGateway.offer.dto.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offer.dto.GetOfferInfoResponse;
import com.rsww.mikolekn.APIGateway.offer.dto.OfferDto;
import com.rsww.mikolekn.APIGateway.offer.dto.OffersDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OfferController {
    private final OfferService offerService;

    OfferController (OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<GetAllOffersResponse> offers(@RequestBody OffersDto offersDto) {
        return offerService.offers(offersDto);
    }

    @PostMapping("/offer")
    public ResponseEntity<GetOfferInfoResponse> offer(@RequestBody OfferDto offerDto) {
        return offerService.offer(offerDto);
    }
}
