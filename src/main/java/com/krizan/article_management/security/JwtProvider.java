package com.krizan.article_management.security;

import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtEncoder jwtEncoder;
    private final AppUserService appUserService;
    @Getter
    private final Long jwtExpirationTimeInMillis = 1_200_000L; // 20 minutes  (60_000L = 1 minute)

    public String generateTokenByAuthentication(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        AppUser appUser = appUserService.getAppUserByUsername(principal.getUsername());
        return generateTokenByAppUser(appUser);
    }

    public String generateTokenByAppUser(AppUser appUser) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationTimeInMillis))
                .subject(appUser.getUsername())
                .claim("role", appUser.getRole())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
