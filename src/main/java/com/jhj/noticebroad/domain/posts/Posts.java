package com.jhj.noticebroad.domain.posts;

import com.jhj.noticebroad.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

//entity에서는 절대로!!! 절대로!! setter를 선언하지 않는다.
@Getter
@Entity
@NoArgsConstructor
public class Posts extends BaseTimeEntity {
    @Id//pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //column를 굳이 선언하지 않아도 자동으로 선언된다. 하지만 적어주는 것이 나중에 더 좋은 것 같다.
    private String author;

    @Builder
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
