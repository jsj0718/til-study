package me.jsj.domain.user.dao.chapter3.v2;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoCh3V2 {
    private final ConnectionMaker connectionMaker;

    public UserDaoCh3V2(ConnectionMaker connectionMaker) {
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
