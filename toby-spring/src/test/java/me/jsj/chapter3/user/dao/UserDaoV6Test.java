package me.jsj.chapter3.user.dao;

import me.jsj.chapter1.user.domain.User;
import me.jsj.chapter3.user.dao.v5.UserDaoV6;
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
class UserDaoV6Test {

    UserDaoV6 userDao;

    @BeforeEach
    void setting() {
        DataSource dataSource = new SimpleDriverDataSource(new Driver(), "jdbc:h2:mem:test", "sa", "");
        userDao = new UserDaoV6(dataSource);

        userDao.add(new User("id", "name", "password"));
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
        User findUser = userDao.get("id");
        assertThat(findUser.getId()).isEqualTo("id");
        assertThat(findUser.getName()).isEqualTo("name");
        assertThat(findUser.getPassword()).isEqualTo("password");
    }

    @Test
    void testGetAllMethod() {
        userDao.deleteAllV2();

        User user1 = new User("gyumee", "name1", "password1");
        User user2 = new User("leegw700", "name2", "password2");
        User user3 = new User("bumjin", "name3", "password3");

        userDao.add(user1); //gyumee
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2); //leegw700
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3); //bumjin
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void testFailGetAll() {
        userDao.deleteAllV2();

        List<User> users = userDao.getAll();
        assertThat(users.size()).isEqualTo(0);
    }
}