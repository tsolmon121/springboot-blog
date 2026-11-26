package me.tuya.controller;

import lombok.RequiredArgsConstructor;
import me.tuya.domain.Article;
import me.tuya.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewContoller {

    private final BlogService blogService;
    @GetMapping("/articles")
    public String getArticles(Model model){
        List<Article> articles = blogService.getAll();
        model.addAttribute("articles", articles);
        return "articles";

    }

}
