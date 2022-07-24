package me.jsj.domain.user.dao;

import me.jsj.domain.user.UserV1;
import me.jsj.domain.user.dao.chapter3.v6.UserDaoCh3V6;
import org.h2.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoCh3V6Test {

    UserDaoCh3V6 userDao;

    @BeforeEach
    void setting() {
        DataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:h2:mem:test", "sa", "");
        userDao = new UserDaoCh3V6(dataSource);

        userDao.add(new UserV1("id", "name", "password"));
    }

    @AfterEach
    void clear() {
        userDao.deleteAllV2();
    }

    @Test
    void testAddAndDeleteMethod() {
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    void testGetMethod() {
        UserV1 findUser = userDao.get("id");
        assertThat(findUser.getId()).isEqualTo("id");
        assertThat(findUser.getName()).isEqualTo("name");
        assertThat(findUser.getPassword()).isEqualTo("password");
    }

    @Test
    void testGetAllMethod() {
        userDao.deleteAllV2();

        UserV1 user1 = new UserV1("gyumee", "name1", "password1");
        UserV1 user2 = new UserV1("leegw700", "name2", "password2");
        UserV1 user3 = new UserV1("bumjin", "name3", "password3");

        userDao.add(user1); //gyumee
        List<UserV1> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2); //leegw700
        List<UserV1> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3); //bumjin
        List<UserV1> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(UserV1 user1, UserV1 user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void testFailGetAll() {
        userDao.deleteAllV2();

        List<UserV1> users = userDao.getAll();
        assertThat(users.size()).isEqualTo(0);
    }
}