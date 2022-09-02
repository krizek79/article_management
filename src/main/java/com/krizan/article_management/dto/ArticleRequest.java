package com.krizan.article_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleRequest {

    private final String title;
    private final String text;
}
