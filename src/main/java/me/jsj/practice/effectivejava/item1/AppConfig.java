package me.jsj.practice.effectivejava.item1;

import me.jsj.hello.KoreanHelloService;
import me.jsj.item1.advantage3.HelloServiceV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloServiceV2 helloServiceV2() {
        return new KoreanHelloService();
    }
}
