package com.krizan.article_management.service.auth;

import com.krizan.article_management.dto.AuthResponse;
import com.krizan.article_management.dto.LoginRequest;
import com.krizan.article_management.dto.RefreshTokenRequest;
import com.krizan.article_management.dto.RegistrationRequest;
import com.krizan.article_management.model.RefreshToken;

public interface AuthService {

    void register(RegistrationRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
}
