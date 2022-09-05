package com.krizan.article_management.configuration;

import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final AppUserService appUserService;

    @Override
    public void run(ApplicationArguments args) {
        RegistrationRequest request = new RegistrationRequest("admin", "admin", "admin");
        appUserService.createAppUser(request, Role.ADMIN);
    }
}
