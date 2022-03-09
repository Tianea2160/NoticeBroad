package com.jhj.noticebroad.web;

import com.jhj.noticebroad.domain.posts.Posts;
import com.jhj.noticebroad.dto.PostsRequestSaveDto;
import com.jhj.noticebroad.dto.PostsResponseDto;
import com.jhj.noticebroad.dto.PostsUpdateRequestDto;
import com.jhj.noticebroad.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PostApiController {

    private final PostsService postsService;

    @Autowired
    private PostApiController(PostsService postsService){
        this.postsService = postsService;
    }
    //create
    @PostMapping("/api/v1/posts")
    public Long save(
            @RequestBody
            PostsRequestSaveDto requestDto
    ){
        return postsService.save(requestDto);
    }
    //update
    @PutMapping("/api/v1/posts/{id}")
    public Long update(
            @PathVariable
            Long id,
            @RequestBody
            PostsUpdateRequestDto requestDto
    ){
        return postsService.update(id, requestDto);
    }
    //read
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto read(
            @PathVariable
            Long id
    ){
        return postsService.findById(id);
    }


    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(
            @PathVariable
            Long id
    ){
        return postsService.deleteById(id);
    }

}
