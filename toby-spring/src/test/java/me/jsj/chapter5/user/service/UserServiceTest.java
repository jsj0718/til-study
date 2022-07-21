package me.jsj.chapter5.user.service;

import me.jsj.chapter5.user.dao.UserDao;
import me.jsj.chapter5.user.domain.Level;
import me.jsj.chapter5.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

import static me.jsj.chapter5.user.service.UserService.MIN_LOGIN_COUNT_FOR_SILVER;
import static me.jsj.chapter5.user.service.UserService.MIN_RECOMMEND_COUNT_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    List<User> users;

    @BeforeEach
    void setup() {
        users = Arrays.asList(
                new User("1", "채치수", "test1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER -1, 0),
                new User("2", "강백호", "test2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
                new User("3", "서태웅", "test3", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD-1),
                new User("4", "정대만", "test4", Level.SILVER, 60, MIN_RECOMMEND_COUNT_FOR_GOLD),
                new User("5", "송태섭", "test5", Level.GOLD, 100, Integer.MAX_VALUE)
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

/*
    @Test
    void upgradeLevels() {
        users.stream().forEach(user -> userDao.add(user));

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User updateUser = userDao.get(user.getId());
        assertThat(updateUser.getLevel()).isEqualTo(expectedLevel);
    }
*/
    @Test
    void upgradeLevels() {
        users.stream().forEach(user -> userDao.add(user));

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User updateUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(updateUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    void add() {
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevelRead.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(userWithoutLevelRead.getLevel());
    }

    static class TestUserService extends UserService {
        private String id;

        private TestUserService(UserDao userDao, PlatformTransactionManager transactionManager, String id) {
            super(userDao, transactionManager);
            this.id = id;
        }

        @Override
        public void upgradeLevel(User user) {
            if (user.getId().equals(id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }

    }

    static class TestUserServiceException extends RuntimeException {
    }

    @Test
    void upgradeAllOrNothing() {
        TestUserService testUserService = new TestUserService(userDao, transactionManager, users.get(3).getId());

        users.stream().forEach(user -> userDao.add(user));

        try {
            testUserService.upgradeLevels();
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

}