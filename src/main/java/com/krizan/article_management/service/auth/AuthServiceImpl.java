package com.krizan.article_management.service.auth;

import com.krizan.article_management.dto.AuthResponse;
import com.krizan.article_management.dto.LoginRequest;
import com.krizan.article_management.dto.RefreshTokenRequest;
import com.krizan.article_management.dto.RegistrationRequest;
import com.krizan.article_management.exception.NotFoundException;
import com.krizan.article_management.model.RefreshToken;
import com.krizan.article_management.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void register(RegistrationRequest request) {

    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public RefreshToken generateRefreshToken() {
        return refreshTokenRepository.save(new RefreshToken());
    }

    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new NotFoundException("Token is not valid.")
        );
    }
}
