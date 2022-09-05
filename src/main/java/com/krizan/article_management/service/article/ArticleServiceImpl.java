package com.krizan.article_management.service.article;

import com.krizan.article_management.dto.ArticleRequest;
import com.krizan.article_management.exception.IllegalOperationException;
import com.krizan.article_management.exception.NotFoundException;
import com.krizan.article_management.exception.ForbiddenException;
import com.krizan.article_management.model.AppUser;
import com.krizan.article_management.model.Article;
import com.krizan.article_management.model.Role;
import com.krizan.article_management.repository.ArticleRepository;
import com.krizan.article_management.service.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final AppUserService appUserService;

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Article with ID: " + id + " does not exist.")
        );
    }

    @Override
    public Article createArticle(ArticleRequest request) {
        if (request.getTitle().isEmpty() || request.getTitle() == null) {
            throw new IllegalOperationException("Title cannot be empty.");
        }
        if (request.getText().isEmpty() || request.getText() == null) {
            throw new IllegalOperationException("Text cannot be empty");
        }

        AppUser currentAppUser = appUserService.getCurrentAppUser();

        Article article = Article.builder()
                .author(currentAppUser)
                .title(request.getTitle())
                .text(request.getText())
                .build();

        return articleRepository.save(article);
    }

    @Override
    public Article editArticle(Long id, ArticleRequest request) {
        Article article = getArticleById(id);

        AppUser currentAppUser = appUserService.getCurrentAppUser();
        if (currentAppUser != article.getAuthor() && !currentAppUser.getRole().equals(Role.ADMIN))
            throw new ForbiddenException("You don't have permission to edit this article.");

        if (request.getTitle() != null || !request.getTitle().isEmpty())
            article.setTitle(request.getTitle());
        if (request.getText() != null || !request.getText().isEmpty())
            article.setText(request.getText());

        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = getArticleById(id);

        AppUser currentAppUser = appUserService.getCurrentAppUser();
        if (currentAppUser != article.getAuthor() && !currentAppUser.getRole().equals(Role.ADMIN))
            throw new ForbiddenException("You don't have permission to delete this article.");

        articleRepository.delete(article);
    }
}
