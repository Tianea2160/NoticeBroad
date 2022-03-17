package com.jhj.noticebroad.domain.posts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhj.noticebroad.dto.PostsRequestSaveDto;
import com.jhj.noticebroad.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostRepositoryTest {

    @Autowired
    PostsRepository postRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    //책에서는 junit 4 로 작성되었기 때문에 @After 어노테이션을 사용해야했지만 junit 5에서는 @AfterEach를 사용해야한다.
    @AfterEach
    @WithMockUser(roles = "USER")
    void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void POST_등록된다() throws Exception {
        String title = "title";
        String content = "hello world!";
        String url = "http://localhost:" + port + "/api/v1/posts";

        PostsRequestSaveDto requestDto = new PostsRequestSaveDto(title, content, "jhj");

        //when

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> all = postRepository.findAllDesc();

        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo("jhj");
        assertThat(all.get(0).getId()).isGreaterThan(0L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void POST_수정된다() throws Exception {
        String title = "title";
        String content = "hello world!";

        Posts savedPosts = postRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("cho2160@naver.com")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";


        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    void AuditingTest() {
        LocalDateTime now = LocalDateTime.now();

        Posts savedPosts = postRepository.save(
                Posts.builder()
                        .content("content")
                        .author("author")
                        .title("title").build()
        );

        assertThat(savedPosts.getCreateDate()).isAfter(now);
    }
}