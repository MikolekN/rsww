package com.rsww.mikolekn.APIGateway.country.service;

import com.rsww.mikolekn.APIGateway.country.dto.CountryOfferResponse;
import com.rsww.mikolekn.APIGateway.country.dto.CountryRequest;
import com.rsww.mikolekn.APIGateway.country.dto.CountryResponse;
import com.rsww.mikolekn.APIGateway.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue countryQueue;

    @Autowired
    public CountryService(RabbitTemplate rabbitTemplate, Queue countryQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.countryQueue = countryQueue;
        this.rabbitTemplate.setReplyTimeout(10000);
    }

    public ResponseEntity<CountryResponse> getCountries() {
        String requestNumber = RequestUtils.generateRequestNumber();
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a country request with uuid: {}", requestNumber, uuid);

        try {
            CountryRequest countryRequest = new CountryRequest(uuid);
            CountryOfferResponse countryOfferResponse = rabbitTemplate.convertSendAndReceiveAsType(
                    countryQueue.getName(),
                    countryRequest,
                    new ParameterizedTypeReference<>() {}
            );

            if (countryOfferResponse != null) {
                logger.info("{} Received a country response: {}", requestNumber, countryOfferResponse);
                CountryResponse countryResponse = new CountryResponse(uuid, true, countryOfferResponse.getCountries());
                return RequestUtils.prepareResponse(countryResponse);
            } else {
                logger.error("{} Country response is null", requestNumber);
                return ResponseEntity.status(500).build();
            }
        } catch (Exception e) {
            logger.error("{} Exception during country process: {}", requestNumber, e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
