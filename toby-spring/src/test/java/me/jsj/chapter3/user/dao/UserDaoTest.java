package me.jsj.chapter3.user.dao;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;
import me.jsj.chapter1.user.dao.v3.NConnectionMaker;
import me.jsj.chapter1.user.domain.User;
import me.jsj.chapter3.user.dao.v3.strategy.UserDaoV4;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoTest {

    UserDaoV4 userDao;

    @BeforeEach
    void setting() throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        userDao = new UserDaoV4(connectionMaker);

        User user = new User("id", "name", "password");
        userDao.addV3(user);
    }

    @AfterEach
    void clear() throws SQLException, ClassNotFoundException {
        userDao.deleteAllV3();
    }

    @Test
    void testDelete() throws SQLException, ClassNotFoundException {
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.deleteAllV2();

        assertThat(userDao.getCount()).isEqualTo(0);
    }

}
