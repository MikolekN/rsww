package com.rsww.mikolekn.UserService.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMessage {
    private String username;
    private String password;

    private static final Logger logger = LoggerFactory.getLogger(UserMessage.class);

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("Error converting to JSON: {}", e.getMessage());
            return null;
        }
    }

    public static UserMessage fromJSON(String json) {
        json = json.replace('\'', '"').replaceAll("\\s+", "");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, UserMessage.class);
        } catch (JsonProcessingException e) {
            logger.error("Error converting from JSON: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return toJSON();
    }
}
