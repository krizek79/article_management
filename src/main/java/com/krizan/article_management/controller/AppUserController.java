package com.krizan.article_management.controller;

import com.krizan.article_management.dto.response.AppUserResponse;
import com.krizan.article_management.dto.request.CreateAppUserRequest;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appUser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> getAppUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new AppUserResponse(appUserService.getAppUserById(id)), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AppUserResponse> getAllAppUsers() {
        return appUserService.getAllAppUsers()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createAppUser(@RequestBody CreateAppUserRequest request) {
        appUserService.createAppUser(request.getRegistrationRequest(), request.getRole());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAppUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUser(id);
    }
}
