package com.example.transportation.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemoveFlightCommand {
    @JsonProperty("uuid")
    private UUID uuid;
}
