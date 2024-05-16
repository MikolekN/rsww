package com.rsww.mikolekn.APIGateway.country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;

@Service
public class CountryService {
    private final RabbitTemplate rabbitTemplate;
    static Logger logger = LoggerFactory.getLogger(CountryService.class);
    private final Queue countryQueue;

    @Autowired
    CountryService(RabbitTemplate rabbitTemplate, Queue countryQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.countryQueue = countryQueue;
        this.rabbitTemplate.setReplyTimeout(10000);
    }
    public ResponseEntity<CountryResponse> country() {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        UUID uuid = UUID.randomUUID();
        logger.info("{} Started a country request with uuid: {}", requestNumber, uuid);

        CountryOfferResponse countryResponse = rabbitTemplate.convertSendAndReceiveAsType(
                countryQueue.getName(),
                new CountryRequest(uuid),
                new ParameterizedTypeReference<>() {});
        logger.info("{} Received a countries response: {}", requestNumber, countryResponse);
        CountryResponse response = new CountryResponse(UUID.randomUUID(), true, countryResponse.getCountries());
        return prepareResponse(response);
    }
}
