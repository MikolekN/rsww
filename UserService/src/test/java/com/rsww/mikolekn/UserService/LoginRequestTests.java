package com.rsww.mikolekn.UserService;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoginRequestTests {

    @Test
    public void testJSONSerialization() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .uuid(UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"))
                .username("user1")
                .password("password1")
                .build();

        // When
        String json = loginRequest.toJSON();

        // Then
        assertNotNull(json);
        assertEquals("{\"uuid\":\"e58ed763-928c-4155-bee9-fdbaaadc15f3\",\"username\":\"user1\",\"password\":\"password1\"}", json);
    }

    @Test
    public void testJSONDeserialization() {
        // Given
        String json = "{\"uuid\":\"e58ed763-928c-4155-bee9-fdbaaadc15f3\",\"username\":\"user2\",\"password\":\"password2\"}";

        // When
        LoginRequest loginRequest = LoginRequest.fromJSON(json);

        // Then
        assertNotNull(loginRequest);
        assertEquals(UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"), loginRequest.getUuid());
        assertEquals("user2", loginRequest.getUsername());
        assertEquals("password2", loginRequest.getPassword());
    }

    @Test
    public void testToString() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .uuid(UUID.fromString("e58ed763-928c-4155-bee9-fdbaaadc15f3"))
                .username("user3")
                .password("password3")
                .build();

        // When
        String stringRepresentation = loginRequest.toString();

        // Then
        assertNotNull(stringRepresentation);
        assertEquals("{\"uuid\":\"e58ed763-928c-4155-bee9-fdbaaadc15f3\",\"username\":\"user3\",\"password\":\"password3\"}", stringRepresentation);
    }
}
