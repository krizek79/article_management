package com.krizan.article_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationRequest {

    private final String username;
    private final String password;
    private final String repeatPassword;
}
