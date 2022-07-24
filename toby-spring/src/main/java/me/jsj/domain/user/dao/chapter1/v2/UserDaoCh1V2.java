package me.jsj.domain.user.dao.chapter1.v2;

import me.jsj.domain.user.UserV1;

import java.sql.*;

public abstract class UserDaoCh1V2 {
    public void add(UserV1 user) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();

        PreparedStatement ps = conn.prepareStatement("insert into USERS(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public UserV1 get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();

        PreparedStatement ps = conn.prepareStatement("select * from USERS where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        UserV1 user = new UserV1();
        if (rs.next()) {
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        conn.close();

        return user;
    }

/*
    //리팩토링 - 추상화 전
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", "sa", "");
    }
*/

    //리팩토링 - 추상화 후
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
