package com.krizan.article_management.service.appUser;

import com.krizan.article_management.dto.RegistrationRequest;
import com.krizan.article_management.exception.NotFoundException;
import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username " + username + " does not exist.")
        );
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
        return appUserRepository.findByUsername(principal.getSubject()).orElseThrow(
                () -> new NotFoundException("User not found.")
        );
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser createAppUser(RegistrationRequest request, Role role) {
        return null;
    }

    @Override
    public void deleteAppUser(Long id) {
        appUserRepository.delete(getAppUserById(id));
    }
}
