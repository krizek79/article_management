package com.krizan.article_management.controller;

import com.krizan.article_management.dto.request.ArticleRequest;
import com.krizan.article_management.dto.response.ArticleResponse;
import com.krizan.article_management.service.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READER', 'ADMIN', 'WRITER')")
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles()
                .stream()
                .map(ArticleResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READER', 'ADMIN', 'WRITER')")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new ArticleResponse(articleService.getArticleById(id)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WRITER')")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest request) {
        return new ResponseEntity<>(new ArticleResponse(articleService.createArticle(request)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WRITER')")
    public ResponseEntity<ArticleResponse> editArticle(@PathVariable("id") Long id, @RequestBody ArticleRequest request) {
        return new ResponseEntity<>(new ArticleResponse(articleService.editArticle(id, request)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WRITER')")
    public void deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
    }
}
