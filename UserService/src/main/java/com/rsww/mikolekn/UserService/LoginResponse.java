package com.rsww.mikolekn.user_ms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private UUID uuid;
    private boolean response;

    static Logger logger = LoggerFactory.getLogger(LoginResponse.class);

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.info("Error converting to JSON object {} with UUID: {}", this.hashCode(), this.uuid);
            logger.error(e.getMessage());
            return null;
        }
    }

    public static LoginResponse fromJSON(String json) {
        json = json.replace('\'', '"');
        json = json.replaceAll("\\s+", "");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, LoginResponse.class);
        } catch (JsonProcessingException e) {
            logger.info("Error converting from JSON message {}", json);
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
