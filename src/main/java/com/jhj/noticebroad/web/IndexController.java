package com.jhj.noticebroad.web;

import com.jhj.noticebroad.dto.PostsResponseDto;
import com.jhj.noticebroad.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

    private final PostsService postsService;

    @Autowired
    public IndexController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String save(){
        return "post-save";
    }

    @GetMapping("/posts/update/{id}")
    public String update(
            @PathVariable
            Long id,
            Model model
    ){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "post-update";
    }
}
