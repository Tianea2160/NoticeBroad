package com.jhj.noticebroad.web;


import com.jhj.noticebroad.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(
            @RequestParam(value = "name")
            String name,
            @RequestParam(value = "amount")
            int amount
    ){
      return new HelloResponseDto(name, amount);
    }
}
