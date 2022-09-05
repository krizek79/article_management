package com.krizan.article_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenRequest {

    private final String refreshToken;
    private final String username;
}
