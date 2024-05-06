package com.rsww.mikolekn.UserService;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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