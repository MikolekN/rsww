package com.rsww.mikolekn.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private UUID uuid;
    private String username;
    private String password;

    static Logger logger = LoggerFactory.getLogger(LoginRequest.class);

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.info("Error converting to JSON object {} with username: {}, and password: {}", this.hashCode(), this.username, this.password);
            logger.error(e.getMessage());
            return null;
        }
    }

    public static LoginRequest fromJSON(String json) {
        json = json.replace('\'', '"');
        json = json.replaceAll("\\s+", "");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, LoginRequest.class);
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
