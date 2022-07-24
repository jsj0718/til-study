package me.jsj.domain.user.dao.chapter1.v4;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;
import me.jsj.domain.user.UserV1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoCh1V4 {
    private ConnectionMaker connectionMaker;

    public UserDaoCh1V4(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(UserV1 user) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement("insert into USERS(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public UserV1 get(String id) throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

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

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection conn = connectionMaker.makeConnection();

        PreparedStatement ps = conn.prepareStatement("select count(*) from USERS");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return count;
    }
}
