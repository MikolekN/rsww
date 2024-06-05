package com.rsww.mikolekn.APIGateway.socket;

import com.rsww.mikolekn.APIGateway.socket.DTO.OfferReserved;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public SocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendOfferReservedInfo(String dateFrom, String dateTo, String hotelUuid) {
        OfferReserved offerReserved = new OfferReserved("Ktoś właśnie zarezerwował ofertę", dateFrom, dateTo, hotelUuid);
        this.messagingTemplate.convertAndSend("/topic/live-reservation", offerReserved);
    }
}
