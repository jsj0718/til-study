package me.jsj.item5.springioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        SpellChecker spellChecker = (SpellChecker) applicationContext.getBean("spellChecker");
        System.out.println(spellChecker.isValid("word"));
    }
}
