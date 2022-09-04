package com.krizan.article_management.dto;

import com.krizan.article_management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAppUserRequest {

    private final RegistrationRequest registrationRequest;
    private final Role role;
}
