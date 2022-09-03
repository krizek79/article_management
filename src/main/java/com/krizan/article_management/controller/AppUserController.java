package com.krizan.article_management.controller;

import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appUser")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
}
