package com.rsww.mikolekn.APIGateway.offer;

import com.rsww.mikolekn.APIGateway.offer.DTO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.rsww.mikolekn.APIGateway.utils.RequestUtils.prepareResponse;

@Service
public class OfferService {
    private final RabbitTemplate rabbitTemplate;
    static Logger logger = LoggerFactory.getLogger(OfferService.class);
    private final Queue offersQueue;
    private final Queue offerQueue;

    @Autowired
    public OfferService(RabbitTemplate rabbitTemplate, Queue offersQueue, Queue offerQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.offersQueue = offersQueue;
        this.offerQueue = offerQueue;
    }

    public ResponseEntity<GetAllOffersResponse> offers(OffersDto offersDto) {
        UUID uuid = UUID.randomUUID();
        LocalDate dateFrom = LocalDate.parse(offersDto.dateFrom());
        LocalDate dateTo = LocalDate.parse(offersDto.dateTo());

        rabbitTemplate.setReplyTimeout(10000);

        GetAllOffersResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                offersQueue.getName(),
                new GetAllOffersRequest(uuid, offersDto.country(), dateFrom, dateTo, Integer.parseInt(offersDto.numberOfAdults()), Integer.parseInt(offersDto.numberOfChildrenUnder10()), Integer.parseInt(offersDto.numberOfChildrenUnder18())),
                new ParameterizedTypeReference<>() {});
        return prepareResponse(response);
    }

    public ResponseEntity<GetOfferInfoResponse> offer(OfferDto offerDto) {
        UUID uuid = UUID.randomUUID();
        LocalDate dateFrom = LocalDate.parse(offerDto.dateFrom());
        LocalDate dateTo = LocalDate.parse(offerDto.dateTo());
        System.out.println(offerDto);
        GetOfferInfoResponse response = rabbitTemplate.convertSendAndReceiveAsType(
                offerQueue.getName(),
                new GetOfferInfoRequest(uuid, UUID.fromString(offerDto.hotelUuid()), dateFrom, dateTo, Integer.parseInt(offerDto.numberOfAdults()), Integer.parseInt(offerDto.numberOfChildrenUnder10()), Integer.parseInt(offerDto.numberOfChildrenUnder18())),
                new ParameterizedTypeReference<>() {});
        return prepareResponse(response);
    }
}
