package me.tuya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.tuya.domain.Article;
import me.tuya.dto.AddArticleRequest;
import me.tuya.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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

        //when : 게시글 등록 요청
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(reqBody));
        //then : db에 record 1개가 등록되어 있고 그 record의 내용이 위의 것과 동일한지
        //result.andExpect(status().isCreated()).andExpect(jsonPath("$.content").value(""));
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);
    }

}