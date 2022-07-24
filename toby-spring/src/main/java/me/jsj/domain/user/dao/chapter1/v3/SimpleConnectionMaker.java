package me.jsj.domain.user.dao.chapter1.v3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 문제점 : 기능을 확장하여 여러 구현체를 만들 수 없다.
 * 1. 메소드 이름이 정해지지 않았다.
 * 2. UserDao가 DB 커넥션을 제공하는 클래스를 구체적으로 알아야 한다.
 */
public class SimpleConnectionMaker {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", "sa", "");
    }
}
