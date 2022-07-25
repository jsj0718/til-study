package me.jsj.practice.factorybean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MessageFactoryBeanTest {

    @Autowired
    ApplicationContext context;

    @Test
    void getMessageFromFactoryBean() {
        Object message = context.getBean("message");

        assertThat(message).isExactlyInstanceOf(Message.class);
        assertThat(((Message) message).getText()).isEqualTo("Factory Bean");
    }

    @Test
    void getFactoryBean() {
        Object message = context.getBean("&message");
        assertThat(message).isExactlyInstanceOf(MessageFactoryBean.class);
    }
}