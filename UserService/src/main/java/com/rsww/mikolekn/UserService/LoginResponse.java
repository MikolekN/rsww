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
public class LoginResponse {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("response")
    private boolean response = false;
}
