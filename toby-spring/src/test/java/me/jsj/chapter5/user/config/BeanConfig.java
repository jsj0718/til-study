package me.jsj.chapter5.user.config;

import me.jsj.chapter5.user.service.DummyMailSender;
import me.jsj.chapter5.user.service.MockMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

    @Bean
    public MailSender mailSender() {
        return new MockMailSender();
    }
}
