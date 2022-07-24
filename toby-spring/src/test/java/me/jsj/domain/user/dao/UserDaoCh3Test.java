package me.jsj.domain.user.dao;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;
import me.jsj.domain.user.dao.chapter1.v3.NConnectionMaker;
import me.jsj.domain.user.UserV1;
import me.jsj.domain.user.dao.chapter3.v3andv4.strategy.UserDaoCh3V3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoCh3Test {

    UserDaoCh3V3 userDao;

    @BeforeEach
    void setting() throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        userDao = new UserDaoCh3V3(connectionMaker);

        UserV1 user = new UserV1("id", "name", "password");
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
