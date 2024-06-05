package com.rsww.mikolekn.APIGateway.country.controller;

import com.rsww.mikolekn.APIGateway.country.dto.CountryResponse;
import com.rsww.mikolekn.APIGateway.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private static final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<CountryResponse> getCountries() {
        log.info("Country request received");
        return countryService.getCountries();
    }
}