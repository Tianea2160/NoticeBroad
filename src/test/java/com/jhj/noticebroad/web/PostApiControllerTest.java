package com.jhj.noticebroad.web;

import com.jhj.noticebroad.domain.posts.Posts;
import com.jhj.noticebroad.domain.posts.PostsRepository;
import com.jhj.noticebroad.dto.PostsRequestSaveDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    void clean_up_db(){
        postsRepository.deleteAll();
    }
    @Test
    void TestRestApi(){
        String title = "title";
        String content = "content";

        PostsRequestSaveDto dto = PostsRequestSaveDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://127.0.0.1:" + port + "/api/v1/posts";


        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, dto, Long.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //post를 하고 나서 정상적으로 작업이 수행되면 저장된 pk숫자를 반환하는데 이를 검사
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        Posts posts = all.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

}