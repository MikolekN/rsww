package com.rsww.mikolekn.APIGateway.offer;

import com.rsww.mikolekn.APIGateway.offer.DTO.GetAllOffersResponse;
import com.rsww.mikolekn.APIGateway.offer.DTO.GetOfferInfoResponse;
import com.rsww.mikolekn.APIGateway.offer.DTO.OfferDto;
import com.rsww.mikolekn.APIGateway.offer.DTO.OffersDto;
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

    @GetMapping("/offer")
    public ResponseEntity<GetOfferInfoResponse> offer(@RequestBody OfferDto offerDto) {
        return offerService.offer(offerDto);
    }
}
