package com.jhj.noticebroad.web;

import com.jhj.noticebroad.config.auth.LoginUser;
import com.jhj.noticebroad.config.auth.dto.SessionUser;
import com.jhj.noticebroad.domain.user.User;
import com.jhj.noticebroad.dto.PostsResponseDto;
import com.jhj.noticebroad.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null){
            model.addAttribute("userName", user.getName());
        }

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
