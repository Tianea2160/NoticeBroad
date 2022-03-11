package com.jhj.noticebroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NoticeBroadApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeBroadApplication.class, args);
    }

}
