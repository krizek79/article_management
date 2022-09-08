package com.krizan.article_management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Article Management"))
public class ArticleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleManagementApplication.class, args);
    }

}
