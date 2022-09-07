package com.krizan.article_management.service.auth;

import com.krizan.article_management.dto.request.LoginRequest;
import com.krizan.article_management.dto.request.RefreshTokenRequest;
import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.dto.response.AuthResponse;
import com.krizan.article_management.exception.NotFoundException;
import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.RefreshToken;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.repository.RefreshTokenRepository;
import com.krizan.article_management.security.JwtProvider;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AppUserService appUserService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegistrationRequest request) {
        appUserService.createAppUser(request, Role.READER);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateTokenByAuthentication(authentication);

        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
                .username(request.getUsername())
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        validateRefreshToken(request.getRefreshToken());
        AppUser appUser = appUserService.getAppUserByUsername(request.getUsername());
        String token = jwtProvider.generateTokenByAppUser(appUser);
        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(request.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
                .username(request.getUsername())
                .build();
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
