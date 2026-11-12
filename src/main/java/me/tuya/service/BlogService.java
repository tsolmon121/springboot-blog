package me.tuya.service;

import lombok.RequiredArgsConstructor;
import me.tuya.domain.Article;
import me.tuya.dto.AddArticleRequest;
import me.tuya.dto.UpdateArticleRequest;
import me.tuya.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest dto){

        return blogRepository.save(dto.toEntity());
    }

    public List<Article> getAll(){
        return blogRepository.findAll();
    }

    public Article getById(Long id) {
        return blogRepository.findById(id).get();
    }

    public void deleteArticle(Long id){
        blogRepository.deleteById(id);
    }

    public Article updateArticle (Long id, UpdateArticleRequest req){
        Article article = new Article(req.getTitle(), req.getContent());
        article.setId(id);
        blogRepository.save(article);
        return article;
    }

}
