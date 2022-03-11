package com.jhj.noticebroad.dto;

import com.jhj.noticebroad.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private final Long id;
    private final  String title;
    private final String author;
    private final LocalDateTime modifiedData;
    public PostsListResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getAuthor();
        this.author = posts.getAuthor();
        this.modifiedData = posts.getModifiedDate();
    }
}
