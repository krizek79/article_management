package com.krizan.article_management.controller;

import com.krizan.article_management.dto.AppUserResponse;
import com.krizan.article_management.dto.CreateAppUserRequest;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appUser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> getAppUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new AppUserResponse(appUserService.getAppUserById(id)), HttpStatus.OK);
    }

    @GetMapping
    public List<AppUserResponse> getAllAppUsers() {
        return appUserService.getAllAppUsers()
                .stream()
                .map(AppUserResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void createAppUser(@RequestBody CreateAppUserRequest request) {
        appUserService.createAppUser(request.getRegistrationRequest(), request.getRole());
    }

    @DeleteMapping("/{id}")
    public void deleteAppUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUser(id);
    }
}
