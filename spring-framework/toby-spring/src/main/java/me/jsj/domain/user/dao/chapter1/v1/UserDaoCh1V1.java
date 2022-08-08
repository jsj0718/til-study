package me.jsj.domain.user.dao.chapter1.v1;

import me.jsj.domain.user.UserV1;

import java.sql.*;

public class UserDaoCh1V1 {

    public void add(UserV1 user) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
                "sa",
                "");

        PreparedStatement ps = conn.prepareStatement("insert into USERS(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public UserV1 get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
                "sa",
                "");

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

    public void deleteAll() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
                "sa",
                "");

        PreparedStatement ps = conn.prepareStatement("delete from USERS");
        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
