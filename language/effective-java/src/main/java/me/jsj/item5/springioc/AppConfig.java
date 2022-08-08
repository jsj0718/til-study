package me.jsj.item5.springioc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = AppConfig.class)
public class AppConfig {

/*
    @Bean
    public SpellChecker spellChecker(Dictionary dictionary) {
        return new SpellChecker(dictionary);
    }

    @Bean
    public Dictionary springDictionary() {
        return new SpringDictionary();
    }
*/

}
