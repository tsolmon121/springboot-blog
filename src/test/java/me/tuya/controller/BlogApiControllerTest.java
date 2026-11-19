package me.tuya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.tuya.domain.Article;
import me.tuya.dto.AddArticleRequest;
import me.tuya.dto.UpdateArticleRequest;
import me.tuya.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BlogApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // java object <-> JSON String

    @Autowired
    private BlogRepository blogRepository;

    @DisplayName("Blog 글 추가에 성공")
    @Test
    public void addArticle() throws Exception{
        //given : data, url request
        String title = "11월 4일 마지막 수업 시간";
        String content = "JUnit으로 blog 글 생성요청 처리가 성공하는지 실습";
        AddArticleRequest req = new AddArticleRequest(title, content);
        String reqBody = objectMapper.writeValueAsString(req);
        String url = "/api/articles";
        long beforeCount = blogRepository.count();
        //when : 게시글 등록 요청
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqBody));
        //then : db에 record 1개가 등록되어 있고 그 record의 내용이 위의 것과 동일한지
        //result.andExpect(status().isCreated()).andExpect(jsonPath("$.content").value(""));
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(blogRepository.count()).isEqualTo(beforeCount + 1);
//        assertThat(articles.size()).isEqualTo(1);
//        assertThat(articles.get(0).getTitle()).isEqualTo(title);
//        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("Blog 전제 글 조회 성공")
    @Test
    public void getAllArticles() throws Exception{
        //given
        Article article1 = new Article("java","java tutorial");
        Article article2 = new Article("spring","spring tutorial");
        blogRepository.save(article1);
        blogRepository.save(article2);
        long count = blogRepository.count();

        //when
        ResultActions result = mockMvc.perform(get("/api/articles"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value((int)count))
                .andExpect(jsonPath("$[?(@.title == 'java')]").exists())
                .andExpect(jsonPath("$[?(@.title == 'spring')]").exists());
    }

    @DisplayName("Blog 특정 글 조회에 성공")
    @Test
    public void getArticle() throws Exception{
        //given
        Article article = new Article("java","java tutorial");
        Article savedArticle = blogRepository.save(article);
        Long id = savedArticle.getId();

        //when
        ResultActions result = mockMvc.perform(get("/api/articles/{id}", id));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("java"))
                .andExpect(jsonPath("$.content").value("java tutorial"));
    }

    @DisplayName("Blog 글 삭제에 성공")
    @Test
    public void deleteArticle() throws Exception{
        //given
        Article article = new Article("java", "java tutorial");
        Article savedArticle = blogRepository.save(article);
        Long id = savedArticle.getId();

        //when
        ResultActions result = mockMvc.perform(delete("/api/articles/{id}",id));

        //then
        result.andExpect(status().isOk());
        assertThat(blogRepository.findById(id).isEmpty()).isTrue();
    }

    @DisplayName("Blog 글 수정에 성공")
    @Test
    public void updateArticle() throws Exception{
        //given
        Article article = new Article("java","java tutorial");
        Article savedArticle = blogRepository.save(article);
        Long id = savedArticle.getId();
        String title = "updated title";
        String content = "updated content";
        UpdateArticleRequest req = new UpdateArticleRequest(title, content);
        String reqBody = objectMapper.writeValueAsString(req);

        //when
        ResultActions result = mockMvc.perform(put("/api/articles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqBody));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));

        Article updateArticle = blogRepository.findById(id).get();
        assertThat(updateArticle.getTitle()).isEqualTo(title);
        assertThat(updateArticle.getContent()).isEqualTo(content);
    }
}
