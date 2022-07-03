package me.jsj.practice.effectivejava.item1;

import me.jsj.item1.advantage3.HelloServiceV2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloServiceV2 helloServiceV2 = applicationContext.getBean(HelloServiceV2.class);
        System.out.println(helloServiceV2.hello());
    }
}
