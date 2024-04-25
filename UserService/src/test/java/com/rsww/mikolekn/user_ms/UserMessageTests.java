package com.rsww.mikolekn.user_ms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMessageTests {

    @Test
    public void testJSONSerialization() {
        // Given
        UserMessage userMessage = UserMessage.builder()
                .username("user1")
                .password("password1")
                .build();

        // When
        String json = userMessage.toJSON();

        // Then
        assertNotNull(json);
        assertEquals("{\"username\":\"user1\",\"password\":\"password1\"}", json);
    }

    @Test
    public void testJSONDeserialization() {
        // Given
        String json = "{\"username\":\"user2\",\"password\":\"password2\"}";

        // When
        UserMessage userMessage = UserMessage.fromJSON(json);

        // Then
        assertNotNull(userMessage);
        assertEquals("user2", userMessage.getUsername());
        assertEquals("password2", userMessage.getPassword());
    }

    @Test
    public void testToString() {
        // Given
        UserMessage userMessage = UserMessage.builder()
                .username("user3")
                .password("password3")
                .build();

        // When
        String stringRepresentation = userMessage.toString();

        // Then
        assertNotNull(stringRepresentation);
        assertEquals("{\"username\":\"user3\",\"password\":\"password3\"}", stringRepresentation);
    }
}
