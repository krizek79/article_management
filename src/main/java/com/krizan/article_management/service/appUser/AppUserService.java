package com.krizan.article_management.service.appUser;

import com.krizan.article_management.dto.request.RegistrationRequest;
import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {

    AppUser getCurrentAppUser();
    AppUser getAppUserById(Long id);
    AppUser getAppUserByUsername(String username);
    List<AppUser> getAllAppUsers();
    void createAppUser(RegistrationRequest request, Role role);
    void deleteAppUser(Long id);
}
