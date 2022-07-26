package me.jsj.domain.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import me.jsj.domain.user.service.chapter6.UserServiceCh6V1;
import me.jsj.domain.user.service.chapter6.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@RequiredArgsConstructor
@Configuration
public class TestBeanConfig {

    private final UserDaoCh5V1 userDao;
    private final MailSender mailSender;

    @Bean
    public UserServiceCh6V1 testUserService() {
        return new TestUserServiceImpl(userDao, mailSender);
    }

    static class TestUserServiceImpl extends UserServiceImpl {
        private String id = "4";

        protected TestUserServiceImpl(UserDaoCh5V1 userDao, MailSender mailSender) {
            super(userDao, mailSender);
        }

        @Override
        public void upgradeLevel(UserV2 user) {
            if (user.getId().equals(id)) {
                throw new UserServiceCh6Test.TestUserServiceException();
            }
            super.upgradeLevel(user);
        }

    }
}
