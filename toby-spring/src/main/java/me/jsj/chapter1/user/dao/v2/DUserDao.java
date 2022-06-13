package me.jsj.chapter1.user.dao.v2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDaoV2 {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //D사 DB Connection 생성 코드
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", "sa", "");
    }
}
