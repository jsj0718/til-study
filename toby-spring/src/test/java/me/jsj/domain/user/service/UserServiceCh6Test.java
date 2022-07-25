package me.jsj.domain.user.service;

import me.jsj.aop.factorybean.TxProxyFactoryBean;
import me.jsj.aop.handler.TransactionHandler;
import me.jsj.domain.user.Level;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import me.jsj.domain.user.dao.chapter6.MockUserDao;
import me.jsj.domain.user.service.chapter5.MockMailSender;
import me.jsj.domain.user.service.chapter6.UserServiceCh6V1;
import me.jsj.domain.user.service.chapter6.UserServiceImpl;
import me.jsj.domain.user.service.chapter6.UserServiceTx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

import static me.jsj.domain.user.service.chapter6.UserServiceImpl.MIN_LOGIN_COUNT_FOR_SILVER;
import static me.jsj.domain.user.service.chapter6.UserServiceImpl.MIN_RECOMMEND_COUNT_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceCh6Test {

    @Autowired
    ApplicationContext context;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    UserDaoCh5V1 userDao;

    @Autowired
    UserServiceCh6V1 userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    MailSender mailSender;

    List<UserV2> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                new UserV2("1", "채치수", "test1", "test1@email.com", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER -1, 0),
                new UserV2("2", "강백호", "test2", "test2@email.com", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
                new UserV2("3", "서태웅", "test3", "test3@email.com", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD-1),
                new UserV2("4", "정대만", "test4", "test4@email.com", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD),
                new UserV2("5", "송태섭", "test5", "test5@email.com", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @AfterEach
    void clear() {
        userDao.deleteAll();
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void add() {
        UserV2 userWithLevel = users.get(4);
        UserV2 userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        UserV2 userWithLevelRead = userDao.get(userWithLevel.getId());
        UserV2 userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevelRead.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevelRead.getLevel());
    }

    @Test
    void upgradeLevelsV1() {
        //DB 테스트 데이터 준비
        users.stream().forEach(user -> userDao.add(user));

        //메일 발송 여부 확인을 위해 MailSender의 Mock 객체 DI (생성자 주입의 경우 Mock 객체가 Singleton이므로 Clear 필요)
        MockMailSender mockMailSender = (MockMailSender) userServiceImpl.getMailSender();
        mockMailSender.getRequest().clear();

        //테스트 대상 실행
        userService.upgradeLevels();

        //DB에 저장된 결과 확인
        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);

        //Mock 객체를 이용한 결과 확인
        List<String> request = mockMailSender.getRequest();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkLevelUpgraded(UserV2 user, boolean upgraded) {
        UserV2 updateUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    public void upgradeLevelsV2() {
        MockUserDao mockUserDao = new MockUserDao(users);
        MockMailSender mockMailSender = (MockMailSender) userServiceImpl.getMailSender();
        mockMailSender.getRequest().clear();

        UserServiceImpl testUserServiceImpl = new UserServiceImpl(mockUserDao, mockMailSender);

        testUserServiceImpl.upgradeLevels();

        List<UserV2> updated = mockUserDao.getUpdated();
        assertThat(updated.size()).isEqualTo(2);
        checkUserAndLevel(updated.get(0), "2", Level.SILVER);
        checkUserAndLevel(updated.get(1), "4", Level.GOLD);

        List<String> request = mockMailSender.getRequest();
        assertThat(request.size()).isEqualTo(2);
        assertThat(request.get(0)).isEqualTo(users.get(1).getEmail());
        assertThat(request.get(1)).isEqualTo(users.get(3).getEmail());
    }

    private void checkUserAndLevel(UserV2 updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId()).isEqualTo(expectedId);
        assertThat(updated.getLevel()).isEqualTo(expectedLevel);
    }

    @Test
    public void upgradeLevelsV3() {
        //given
        UserDaoCh5V1 mockUserDao = mock(UserDaoCh5V1.class);
        given(mockUserDao.getAll()).willReturn(users);

        MailSender mockMailSender = mock(MailSender.class);

        UserServiceImpl testUserServiceImpl = new UserServiceImpl(mockUserDao, mockMailSender);

        //when
        testUserServiceImpl.upgradeLevels();

        //then
        verify(mockUserDao, times(2)).update(any(UserV2.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(3).getLevel()).isEqualTo(Level.GOLD);

        ArgumentCaptor<SimpleMailMessage> mailMessageArgs = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mockMailSender, times(2)).send(mailMessageArgs.capture());
        List<SimpleMailMessage> mailMessages = mailMessageArgs.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0]).isEqualTo(users.get(1).getEmail());
        assertThat(mailMessages.get(1).getTo()[0]).isEqualTo(users.get(3).getEmail());
    }

    static class TestUserService extends UserServiceImpl {
        private String id;

        private TestUserService(UserDaoCh5V1 userDao, MailSender mailSender, String id) {
            super(userDao, mailSender);
            this.id = id;
        }

        @Override
        public void upgradeLevel(UserV2 user) {
            if (user.getId().equals(id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }

    @Test
    void upgradeAllOrNothingV1() {
        TestUserService testUserService = new TestUserService(userDao, mailSender, users.get(3).getId());

        UserServiceTx userServiceTx = new UserServiceTx(testUserService, transactionManager);

        users.stream().forEach(user -> userDao.add(user));

        try {
            userServiceTx.upgradeLevels();
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    void upgradeAllOrNothingV2() {
        users.stream().forEach(user -> userDao.add(user));

        TestUserService testUserService = new TestUserService(userDao, mailSender, users.get(3).getId());

        TransactionHandler transactionHandler = new TransactionHandler(testUserService, transactionManager, "upgradeLevels");
        UserServiceCh6V1 userService = (UserServiceCh6V1) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{UserServiceCh6V1.class}, transactionHandler);

        try {
            userService.upgradeLevels();
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

    @Test
    @DirtiesContext //컨텍스트 무효화 애노테이션
    void upgradeAllOrNothingV3() throws Exception {
        TestUserService testUserService = new TestUserService(userDao, mailSender, users.get(3).getId());

        TxProxyFactoryBean txProxyFactoryBean = context.getBean("&userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);
        UserServiceCh6V1 txUserService = (UserServiceCh6V1) txProxyFactoryBean.getObject();

        users.stream().forEach(user -> userDao.add(user));

        try {
            txUserService.upgradeLevels();
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

}