package com.rsww.mikolekn.APIGateway.preferences.controller;

import com.rsww.mikolekn.APIGateway.payment.controller.PaymentController;
import com.rsww.mikolekn.APIGateway.preferences.dto.PreferencesResponse;
import com.rsww.mikolekn.APIGateway.preferences.service.PreferencesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PreferencesService preferencesService;

    @Autowired
    public PreferencesController(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<PreferencesResponse> payment(@PathVariable String username) {
        logger.info("Preferences request received for user: {}", username);
        return preferencesService.getPreferences(username);
    }
}
