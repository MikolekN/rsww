package com.rsww.mikolekn.APIGateway.login.controller;

import com.rsww.mikolekn.APIGateway.login.dto.LoginDto;
import com.rsww.mikolekn.APIGateway.login.dto.LoginResponse;
import com.rsww.mikolekn.APIGateway.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        log.info("Login request received for user: {}", loginDto.username());
        return loginService.login(loginDto);
    }
}
