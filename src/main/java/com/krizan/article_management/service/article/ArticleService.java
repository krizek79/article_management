package com.krizan.article_management.service.article;

import com.krizan.article_management.dto.request.ArticleRequest;
import com.krizan.article_management.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getAllArticles();
    Article getArticleById(Long id);
    Article createArticle(ArticleRequest request);
    Article editArticle(Long id, ArticleRequest request);
    void deleteArticle(Long id);
}
