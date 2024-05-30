package com.rsww.mikolekn.UserService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
