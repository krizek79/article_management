package com.krizan.article_management.service.appUser;

import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.exception.IllegalOperationException;
import com.krizan.article_management.exception.NotFoundException;
import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = getAppUserByUsername(username);
        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                appUser.isAccountNonExpired(),
                appUser.isCredentialsNonExpired(),
                appUser.isAccountNonLocked(),
                appUser.getAuthorities()
        );
    }

    @Override
    public AppUser getCurrentAppUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getAppUserByUsername(principal.getSubject());
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public AppUser getAppUserByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser createAppUser(RegistrationRequest request, Role role) {
        if (request.getUsername() == null)
            throw new IllegalOperationException("Username cannot be null.");
        if (request.getUsername().isEmpty())
            throw new IllegalOperationException("Username cannot be empty.");
        if (request.getPassword() == null)
            throw new IllegalOperationException("Password cannot be null.");
        if (request.getPassword().isEmpty())
            throw new IllegalOperationException("Password cannot be empty.");
        if (!request.getPassword().equals(request.getRepeatPassword()))
            throw new IllegalOperationException("Passwords do not match.");

        AppUser appUser = AppUser.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .role(role)
                .articles(new ArrayList<>())
                .locked(false)
                .enabled(true)
                .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(Long id) {
        appUserRepository.delete(getAppUserById(id));
    }
}
