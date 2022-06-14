package me.jsj.chapter1.user.dao.v4;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;
import me.jsj.chapter1.user.dao.v3.NConnectionMaker;
import me.jsj.chapter1.user.dao.v6.CountingConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDaoV4 userDao() {
        return new UserDaoV4(connectionMaker());
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
