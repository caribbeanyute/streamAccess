package me.cleon.streamaccess.service.security;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class JWTRequest {
    String username;
    String password;
}