package com.rsww.mikolekn.APIGateway.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest extends AbstractRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public LoginRequest(UUID uuid, String username, String password) {
        super(uuid);
        this.username = username;
        this.password =  password;
    }
}