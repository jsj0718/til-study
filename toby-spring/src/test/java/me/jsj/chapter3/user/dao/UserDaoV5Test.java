package me.jsj.chapter3.user.dao;

import me.jsj.chapter1.user.domain.User;
import me.jsj.chapter3.user.dao.v4.UserDaoV5;
import org.h2.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoV5Test {
    UserDaoV5 userDao;

    @BeforeEach
    void setting() throws SQLException {
        DataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:h2:mem:test", "sa", "");
        userDao = new UserDaoV5(dataSource);

        userDao.add(new User("id", "name", "password"));
    }

    @AfterEach
    void clear() throws SQLException {
        userDao.deleteAllV3();
    }

    @Test
    void testAddAndDelete() throws SQLException {
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);
    }
}
