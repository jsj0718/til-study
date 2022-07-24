package me.jsj.domain.user.dao;

import me.jsj.domain.user.dao.chapter1.v1.UserDaoCh1V1;
import me.jsj.domain.user.dao.chapter1.v2.DUserDao;
import me.jsj.domain.user.dao.chapter1.v2.UserDaoCh1V2;
import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;
import me.jsj.domain.user.dao.chapter1.v3.NConnectionMaker;
import me.jsj.domain.user.dao.chapter1.v3.UserDaoCh1V3;
import me.jsj.domain.user.dao.chapter1.v4.DaoFactory;
import me.jsj.domain.user.dao.chapter1.v4.UserDaoCh1V4;
import me.jsj.domain.user.dao.chapter1.v6.CountingConnectionMaker;
import me.jsj.domain.user.UserV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoCh1Test {

    @AfterEach
    void clear() throws SQLException, ClassNotFoundException {
        UserDaoCh1V1 userDaoV1 = new UserDaoCh1V1();
        userDaoV1.deleteAll();
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V1() throws SQLException, ClassNotFoundException {
        //given
        UserDaoCh1V1 userDao = new UserDaoCh1V1();

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V2() throws SQLException, ClassNotFoundException {
        //given
        UserDaoCh1V2 userDao = new DUserDao();

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V3() throws SQLException, ClassNotFoundException {
        //given
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDaoCh1V3 userDao = new UserDaoCh1V3(connectionMaker);

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V4() throws SQLException, ClassNotFoundException {
        //given
        DaoFactory daoFactory = new DaoFactory();
        UserDaoCh1V4 userDao = daoFactory.userDao();

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V5() throws SQLException, ClassNotFoundException {
        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoCh1V4 userDao = applicationContext.getBean("userDao", UserDaoCh1V4.class);

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

        //then
        assertThat(findUser.getId()).isEqualTo("test");
        assertThat(findUser.getName()).isEqualTo("테스터");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    void UserDao_동등성비교() {
        //given
        DaoFactory daoFactory = new DaoFactory();
        UserDaoCh1V4 userDao1 = daoFactory.userDao();
        UserDaoCh1V4 userDao2 = daoFactory.userDao();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoCh1V4 userDao3 = applicationContext.getBean("userDao", UserDaoCh1V4.class);
        UserDaoCh1V4 userDao4 = applicationContext.getBean("userDao", UserDaoCh1V4.class);

        //when & then
        assertThat(userDao1).isNotEqualTo(userDao2); //싱긑톤 X
        assertThat(userDao3).isEqualTo(userDao4); //싱글톤 O
    }

    @Test
    void 유저_H2인메모리DB에_등록및조회_V6() throws SQLException, ClassNotFoundException {
        //given
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoCh1V4 userDao = applicationContext.getBean("userDao", UserDaoCh1V4.class);

        CountingConnectionMaker ccm = applicationContext.getBean("connectionMaker", CountingConnectionMaker.class);

        UserV1 user = new UserV1();
        user.setId("test");
        user.setName("테스터");
        user.setPassword("1234");

        //when
        userDao.add(user);
        UserV1 findUser = userDao.get(user.getId());

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
        UserDaoCh1V4 userDao = applicationContext.getBean("userDao", UserDaoCh1V4.class);

        //when & then
        assertThat(userDao.getCount()).isEqualTo(0);

        UserV1 user = new UserV1("test", "테스터", "1234");
        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);
    }

}