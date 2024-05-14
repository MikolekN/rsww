package com.rsww.mikolekn.APIGateway.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RequestUtils {
    public static <T extends AbstractResponse> ResponseEntity<T> prepareResponse(T response) {
        if (response != null) {
            if (response.isResponse()) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
