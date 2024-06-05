package com.rsww.mikolekn.APIGateway.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.*;

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