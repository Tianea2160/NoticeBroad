package com.jhj.noticebroad.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
    }
}
