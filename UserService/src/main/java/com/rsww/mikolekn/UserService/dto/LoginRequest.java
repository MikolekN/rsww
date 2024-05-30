package com.rsww.mikolekn.UserService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
