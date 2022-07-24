package me.jsj.domain.user.dao;

import me.jsj.domain.user.UserV1;
import me.jsj.domain.user.dao.chapter3.v5.UserDaoCh3V5;
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
public class UserDaoCh3V5Test {
    UserDaoCh3V5 userDao;

    @BeforeEach
    void setting() throws SQLException {
        DataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:h2:mem:test", "sa", "");
        userDao = new UserDaoCh3V5(dataSource);

        userDao.add(new UserV1("id", "name", "password"));
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
