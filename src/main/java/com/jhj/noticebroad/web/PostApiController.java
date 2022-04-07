package com.jhj.noticebroad.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhj.noticebroad.domain.posts.Posts;
import com.jhj.noticebroad.dto.PostsRequestSaveDto;
import com.jhj.noticebroad.dto.PostsResponseDto;
import com.jhj.noticebroad.dto.PostsUpdateRequestDto;
import com.jhj.noticebroad.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@RestController
public class PostApiController {
    static Logger LOG = Logger.getAnonymousLogger();

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
    /*
        간편 구현을 위해서 dto를 구현하지 않고 바로 반환을 했습니다.
        실제로 사용할 때에는 dto를 구현해서 필요한 부분만 사용하는 것이 좋을 것 같습니다.
     */
    @GetMapping("/api/v1/posts/paging")
    public ResponseEntity<Page<Posts>> paging(final Pageable pageable){
        Page<Posts> page = postsService.findPage(pageable);
        return ResponseEntity.ok()
                .body(page);
    }
}
