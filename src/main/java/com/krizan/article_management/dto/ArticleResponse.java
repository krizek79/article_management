package com.krizan.article_management.dto;

import com.krizan.article_management.model.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final String text;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.author = article.getAuthor().getUsername();
        this.title = article.getTitle();
        this.text = article.getText();
    }
}
