package com.krizan.article_management.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class AuthResponse {

    private final String authenticationToken;
    private final String refreshToken;
    private final Instant expiresAt;
    private final String username;
}
