package me.jsj.chapter5.user.dao;

import me.jsj.chapter5.user.domain.Level;
import me.jsj.chapter5.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoTest {

    @Autowired
    UserDao userDao;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setup() {
        user1 = new User("1", "정대만", "test1", Level.BASIC, 1, 0);
        user2 = new User("2", "서태웅", "test2", Level.SILVER, 55, 10);
        user3 = new User("3", "강백호", "test3", Level.GOLD, 100, 40);
    }

    @AfterEach
    void clear() {
        userDao.deleteAll();
    }

    @Test
    void addAndGet() {
        userDao.add(user1);
        User getUser1 = userDao.get(user1.getId());
        checkSameUser(user1, getUser1);

        userDao.add(user2);
        User getUser2 = userDao.get(user2.getId());
        checkSameUser(user2, getUser2);
    }


    @Test
    void update() {
        userDao.add(user1);
        userDao.add(user2);

        user1.setName("채치수");
        user1.setPassword("test6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        userDao.update(user1);

        User updateUser1 = userDao.get(user1.getId());
        checkSameUser(user1, updateUser1);
        User updateUser2 = userDao.get(user2.getId());
        checkSameUser(user2, updateUser2);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}