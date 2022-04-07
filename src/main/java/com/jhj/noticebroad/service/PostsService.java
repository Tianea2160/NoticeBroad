package com.jhj.noticebroad.service;

import com.jhj.noticebroad.domain.posts.Posts;
import com.jhj.noticebroad.domain.posts.PostsRepository;
import com.jhj.noticebroad.dto.PostsListResponseDto;
import com.jhj.noticebroad.dto.PostsRequestSaveDto;
import com.jhj.noticebroad.dto.PostsResponseDto;
import com.jhj.noticebroad.dto.PostsUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {

    private final PostsRepository postRepository;

    @Autowired
    public PostsService(PostsRepository postRepository){
        this.postRepository = postRepository;
    }

    @Transactional
    public Long save(PostsRequestSaveDto requestDto){
        return  postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id,PostsUpdateRequestDto requestDto){
        Optional<Posts> posts = postRepository.findById(id);

        posts.ifPresent(p ->{
            p.update(requestDto.getTitle(), requestDto.getContent());
        });
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id의 사용자는 없습니다."));
        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long deleteById(Long id) {
        postRepository.deleteById(id);
        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public Page<Posts> findPage(Pageable pageable){
        return postRepository.findAll(pageable);
    }

}
