package me.tuya.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class UpdateArticleRequest {
    private String title;
    private String content;

}
