package me.jsj.chapter1.user.dao;

import me.jsj.chapter1.user.dao.v1.UserDaoV1;
import me.jsj.chapter1.user.dao.v2.DUserDao;
import me.jsj.chapter1.user.dao.v2.UserDaoV2;
import me.jsj.chapter1.user.dao.v3.ConnectionMaker;
import me.jsj.chapter1.user.dao.v3.NConnectionMaker;
import me.jsj.chapter1.user.dao.v3.UserDaoV3;
import me.jsj.chapter1.user.dao.v4.DaoFactory;
import me.jsj.chapter1.user.dao.v4.UserDaoV4;
import me.jsj.chapter1.user.dao.v6.CountingConnectionMaker;
import me.jsj.chapter1.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoTest {

    @AfterEach
    void clear() throws SQLException, ClassNotFoundException {
        UserDaoV1 userDaoV1 = new UserDaoV1();
        userDaoV1.deleteAll();
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V1() throws SQLException, ClassNotFoundException {
        //given
        UserDaoV1 userDao = new UserDaoV1();

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V2() throws SQLException, ClassNotFoundException {
        //given
        UserDaoV2 userDao = new DUserDao();

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V3() throws SQLException, ClassNotFoundException {
        //given
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDaoV3 userDao = new UserDaoV3(connectionMaker);

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V4() throws SQLException, ClassNotFoundException {
        //given
        DaoFactory daoFactory = new DaoFactory();
        UserDaoV4 userDao = daoFactory.userDao();

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V5() throws SQLException, ClassNotFoundException {
        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoV4 userDao = applicationContext.getBean("userDao", UserDaoV4.class);

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void UserDao_동등성비교() {
        //given
        DaoFactory daoFactory = new DaoFactory();
        UserDaoV4 userDao1 = daoFactory.userDao();
        UserDaoV4 userDao2 = daoFactory.userDao();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoV4 userDao3 = applicationContext.getBean("userDao", UserDaoV4.class);
        UserDaoV4 userDao4 = applicationContext.getBean("userDao", UserDaoV4.class);

        //when & then
        assertThat(userDao1).isNotEqualTo(userDao2); //싱긑톤 X
        assertThat(userDao3).isEqualTo(userDao4); //싱글톤 O
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V6() throws SQLException, ClassNotFoundException {
        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoV4 userDao = applicationContext.getBean("userDao", UserDaoV4.class);

        CountingConnectionMaker ccm = applicationContext.getBean("connectionMaker", CountingConnectionMaker.class);

        User user = new User();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
        assertThat(ccm.getCounter()).isEqualTo(2);
    }

    @Test
    void testGetCount() throws SQLException, ClassNotFoundException {
        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoV4 userDao = applicationContext.getBean("userDao", UserDaoV4.class);

        //when & then
        assertThat(userDao.getCount()).isEqualTo(0);

        User user = new User("test", "테스터", "1234");
        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);
    }

}