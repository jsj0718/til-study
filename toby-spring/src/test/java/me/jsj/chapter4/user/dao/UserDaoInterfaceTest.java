package me.jsj.chapter4.user.dao;

import me.jsj.chapter1.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoInterfaceTest {

    @Autowired
    UserDao userDao;

    @Autowired
    DataSource dataSource;

    @Test
    void duplicateKey() {
        userDao.deleteAll();

        User user1 = new User("1", "test", "test");

        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getRootCause();
            SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            assertThat(translator.translate(null, null, sqlException)).isInstanceOf(DuplicateKeyException.class);
        }
    }
}