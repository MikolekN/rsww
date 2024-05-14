package com.rsww.mikolekn.APIGateway.offer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OfferService {
    public ResponseEntity<OffersResponse> offers(OffersDto offersDto) {
        return new ResponseEntity<>(new OffersResponse(true , "1"), HttpStatus.OK);
    }

    public ResponseEntity<OfferResponse> offer(OfferDto offerDto) {
        return new ResponseEntity<>(new OfferResponse(true , "1"), HttpStatus.OK);
    }
}
