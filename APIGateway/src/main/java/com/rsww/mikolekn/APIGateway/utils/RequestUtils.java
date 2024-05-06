package com.rsww.mikolekn.APIGateway.utils;

import com.rsww.mikolekn.APIGateway.payment.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RequestUtils {
    public static ResponseEntity<Boolean> prepareResponse(AbstractResponse response) {
        if (response != null) {
            if (response.isResponse()) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
