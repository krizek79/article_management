package com.krizan.article_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krizan.article_management.dto.request.LoginRequest;
import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.service.appUser.AppUserService;
import com.krizan.article_management.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @MockBean
    AuthService authService;
    @MockBean
    AppUserService appUserService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void canRegisterAppUser() throws Exception {
        RegistrationRequest request = new RegistrationRequest(
                "user",
                "pass",
                "pass"
        );
        String requestJson = toJson(request);
        mockMvc.perform(post("/api/auth/register")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void cannotRegisterAppUserWithWrongRequest() throws Exception {
        RegistrationRequest request = new RegistrationRequest(
                null,
                "",
                "pass"
        );
        String requestJson = toJson(request);
        mockMvc.perform(post("/api/auth/register")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void canLogin() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "user",
                "pass",
                "pass"
        );
        appUserService.createAppUser(registrationRequest, Role.WRITER);
        LoginRequest loginRequest = new LoginRequest(
                "user",
                "pass"
        );
        String loginRequestJson = toJson(loginRequest);
        mockMvc.perform(post("/api/auth/login")
                .content(loginRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void cannotLoginWithWrongUsername() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "user",
                "pass",
                "pass"
        );
        appUserService.createAppUser(registrationRequest, Role.WRITER);
        LoginRequest loginRequest = new LoginRequest(
                "unknownUser",
                "pass"
        );
        String loginRequestJson = toJson(loginRequest);
        mockMvc.perform(post("/api/auth/login")
                        .content(loginRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cannotLoginWithWrongPassword() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "user",
                "pass",
                "pass"
        );
        appUserService.createAppUser(registrationRequest, Role.WRITER);
        LoginRequest loginRequest = new LoginRequest(
                "user",
                "wrongPass"
        );
        String loginRequestJson = toJson(loginRequest);
        mockMvc.perform(post("/api/auth/login")
                        .content(loginRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private String toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r);
    }
}