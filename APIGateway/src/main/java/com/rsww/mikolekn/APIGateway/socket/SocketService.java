package com.rsww.mikolekn.APIGateway.socket;

import com.rsww.mikolekn.APIGateway.preferences.dto.PreferencesResponse;
import com.rsww.mikolekn.APIGateway.preferences.service.PreferencesService;
import com.rsww.mikolekn.APIGateway.socket.DTO.OfferReserved;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final PreferencesService preferencesService;

    public SocketService(SimpMessagingTemplate messagingTemplate, PreferencesService preferencesService) {
        this.messagingTemplate = messagingTemplate;
        this.preferencesService = preferencesService;
    }

    public void sendOfferReservedInfo(String dateFrom, String dateTo, String hotelUuid) {
        OfferReserved offerReserved = new OfferReserved("Ktoś właśnie zarezerwował ofertę", dateFrom, dateTo, hotelUuid);
        this.messagingTemplate.convertAndSend("/topic/live-reservation", offerReserved);
    }

    public void sendUserPreferences(String username) {
        PreferencesResponse lastChangesResponse = preferencesService.getPreferences(username).getBody();
        this.messagingTemplate.convertAndSend("/topic/user-preferences/" + username, lastChangesResponse);
    }
}
