package com.krizan.article_management.dto.response;

import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.Role;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AppUserResponse {

    private final Long id;
    private final String username;
    private final Role role;
    private final Boolean locked;
    private final Boolean enabled;
    private final List<ArticleResponse> articles = new ArrayList<>();

    public AppUserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.role = appUser.getRole();
        this.locked = !appUser.isAccountNonLocked();
        this.enabled = appUser.isEnabled();
        this.articles.addAll(appUser.getArticles()
                .stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList())
        );
    }
}
