package com.rsww.mikolekn.APIGateway.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Random;

public class RequestUtils {

    public static <T extends AbstractResponse> ResponseEntity<T> prepareResponse(T response) {
        return prepareResponse(response, HttpStatus.UNAUTHORIZED);
    }

    public static <T extends AbstractResponse> ResponseEntity<T> prepareResponse(T response, HttpStatus failureStatus) {
        if (response != null) {
            if (response.isResponse()) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, failureStatus);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String generateRequestNumber() {
        return "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
    }
}
