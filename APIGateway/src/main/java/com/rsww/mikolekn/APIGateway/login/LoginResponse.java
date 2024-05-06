package com.rsww.mikolekn.APIGateway.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ToString
@Getter
@Setter
public class LoginResponse extends AbstractResponse { }
