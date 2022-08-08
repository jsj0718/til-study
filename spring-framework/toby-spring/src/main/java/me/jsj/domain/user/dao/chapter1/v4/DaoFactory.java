package me.jsj.domain.user.dao.chapter1.v4;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;
import me.jsj.domain.user.dao.chapter1.v3.NConnectionMaker;
import me.jsj.domain.user.dao.chapter1.v6.CountingConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDaoCh1V4 userDao() {
        return new UserDaoCh1V4(connectionMaker());
    }

    @Bean
    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }

    @Bean
    public MessageDao messageDao() {
        return new MessageDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new NConnectionMaker();
    }
}
