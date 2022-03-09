package com.jhj.noticebroad.dto;

import com.jhj.noticebroad.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostsRequestSaveDto {
    private final String title;
    private final String content;
    private final String author;


    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();
    }
    @Builder
    public PostsRequestSaveDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "PostsRequestSaveDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
