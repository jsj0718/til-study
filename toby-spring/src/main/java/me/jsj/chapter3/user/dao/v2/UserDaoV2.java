package me.jsj.chapter3.user.dao.v2;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoV2 {
    private final ConnectionMaker connectionMaker;

    public UserDaoV2(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionMaker.makeConnection();

            ps = makeStatement(conn);

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }

    private PreparedStatement makeStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
