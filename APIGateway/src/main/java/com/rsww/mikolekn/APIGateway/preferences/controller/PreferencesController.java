package com.rsww.mikolekn.APIGateway.preferences.controller;

import com.rsww.mikolekn.APIGateway.offerchange.model.FlightRemovedEvent;
import com.rsww.mikolekn.APIGateway.preferences.dto.PreferencesResponse;
import com.rsww.mikolekn.APIGateway.preferences.service.PreferencesService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Controller
public class PreferencesController {
    private final PreferencesService preferencesService;

    public PreferencesController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.PreferencesFrontQueue}")
    public void flightRemovedEventHandler(PreferencesResponse preferencesResponse) {
        preferencesService.notifyPreferencesFrontQueue(preferencesResponse);
    }
}
