package com.krizan.article_management.service.auth;

import com.krizan.article_management.dto.response.AuthResponse;
import com.krizan.article_management.dto.request.LoginRequest;
import com.krizan.article_management.dto.request.RefreshTokenRequest;
import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.model.RefreshToken;

public interface AuthService {

    void register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
}
