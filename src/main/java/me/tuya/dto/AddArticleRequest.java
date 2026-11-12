package me.tuya.dto;

import lombok.Getter;
import me.tuya.domain.Article;

@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public AddArticleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity() {
        return Article.builder().title(title).content(content).build();
    }
}
