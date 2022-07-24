package me.jsj.domain.user.dao;

import me.jsj.domain.user.UserV1;
import me.jsj.domain.user.dao.chapter4.UserDaoCh4V1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoCh4Test {

    @Autowired
    UserDaoCh4V1 userDao;

    @Autowired
    DataSource dataSource;

    @Test
    void duplicateKey() {
        userDao.deleteAll();

        UserV1 user1 = new UserV1("1", "test", "test");

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