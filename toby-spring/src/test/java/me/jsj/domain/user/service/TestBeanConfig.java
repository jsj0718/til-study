package me.jsj.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import me.jsj.domain.user.service.chapter6.UserServiceCh6V1;
import me.jsj.domain.user.service.chapter6.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestBeanConfig {

    private final UserDaoCh5V1 userDao;
    private final MailSender mailSender;

    @Bean
    public UserServiceCh6V1 testUserService() {
        return new TestUserService(userDao, mailSender);
    }

    static class TestUserService extends UserServiceImpl {
        private String id = "4";

        protected TestUserService(UserDaoCh5V1 userDao, MailSender mailSender) {
            super(userDao, mailSender);
        }

        @Override
        public void upgradeLevel(UserV2 user) {
            if (user.getId().equals(id)) {
                throw new UserServiceCh6Test.TestUserServiceException();
            }
            super.upgradeLevel(user);
        }

        @Override
        public List<UserV2> getAll() {
            for (UserV2 user : super.getAll()) {
                log.info("update 시도");
                super.update(user);
                log.info("update 성공");
            }
            return null;
        }
    }
}
