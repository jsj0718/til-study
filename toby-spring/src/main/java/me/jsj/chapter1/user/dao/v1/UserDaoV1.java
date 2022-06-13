package me.jsj.chapter1.user.dao.v1;

import me.jsj.chapter1.user.domain.User;

import java.sql.*;

public class UserDaoV1 {

    public void add(User user) throws ClassNotFoundException, SQLException {
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

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
                "sa",
                "");

        PreparedStatement ps = conn.prepareStatement("select * from USERS where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = new User();
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
