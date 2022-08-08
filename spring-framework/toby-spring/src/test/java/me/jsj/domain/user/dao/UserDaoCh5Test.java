package me.jsj.domain.user.dao;

import me.jsj.domain.user.Level;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoCh5Test {

    @Autowired
    UserDaoCh5V1 userDao;

    UserV2 user1;
    UserV2 user2;
    UserV2 user3;

    @BeforeEach
    void setup() {
        user1 = new UserV2("1", "정대만", "test1", "test1@email.com", Level.BASIC, 1, 0);
        user2 = new UserV2("2", "서태웅", "test2", "test2@email.com", Level.SILVER, 55, 10);
        user3 = new UserV2("3", "강백호", "test3", "test3@email.com", Level.GOLD, 100, 40);
    }

    @AfterEach
    void clear() {
        userDao.deleteAll();
    }

    @Test
    void addAndGet() {
        userDao.add(user1);
        UserV2 getUser1 = userDao.get(user1.getId());
        checkSameUser(user1, getUser1);

        userDao.add(user2);
        UserV2 getUser2 = userDao.get(user2.getId());
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

        UserV2 updateUser1 = userDao.get(user1.getId());
        checkSameUser(user1, updateUser1);
        UserV2 updateUser2 = userDao.get(user2.getId());
        checkSameUser(user2, updateUser2);
    }

    private void checkSameUser(UserV2 user1, UserV2 user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}