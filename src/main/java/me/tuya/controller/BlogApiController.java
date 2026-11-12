package me.tuya.controller;

import lombok.RequiredArgsConstructor;
import me.tuya.domain.Article;
import me.tuya.dto.AddArticleRequest;
import me.tuya.dto.ArticleResponse;
import me.tuya.dto.UpdateArticleRequest;
import me.tuya.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
//    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
//        Article savedArticle = blogService.save(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
//    }
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ArticleResponse(savedArticle));
    }


    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles(){
//        List<Article> articleList =  blogService.getAll();
//        List<ArticleResponse> articleResponseList = new ArrayList<>();
//        for (Article article: articleList){
//            ArticleResponse ar = new ArticleResponse(article);
//            articleResponseList.add(ar);
//        }
        List<ArticleResponse> articleResponseList = blogService.getAll()
                .stream()
                .map(ArticleResponse::new)
//                .map(a->new ArticleResponse(a))
                .toList();
        return ResponseEntity.ok().body(articleResponseList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getArticle (@PathVariable("id") Long id){
        Article article = blogService.getById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle (@PathVariable("id") Long id){
        blogService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle (@PathVariable("id") Long id, @RequestBody UpdateArticleRequest req){
        Article article = blogService.updateArticle(id, req);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

}
