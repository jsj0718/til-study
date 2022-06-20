package me.jsj.chapter3.user.dao.v3.strategy;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;
import me.jsj.chapter1.user.domain.User;

import java.sql.*;

public class UserDaoV4 {
    private final ConnectionMaker connectionMaker;

    public UserDaoV4(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy st) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionMaker.makeConnection();

            ps = st.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectionMaker.makeConnection();

            StatementStrategy strategy = new DeleteAllStrategy();
            ps = strategy.makePreparedStatement(conn);

            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }

    public void deleteAllV2() throws SQLException, ClassNotFoundException {
        StatementStrategy st = new DeleteAllStrategy();
        jdbcContextWithStatementStrategy(st);
    }

    public void deleteAllV3() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(conn -> conn.prepareStatement("delete from users"));
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatementStrategy(st);
    }

    //로컬 클래스 활용
    public void addV2(final User user) throws SQLException, ClassNotFoundException {
        class AddStatement implements StatementStrategy {
/*
            User user;

            public AddStatement(User user) {
                this.user = user;
            }
*/

            @Override
            public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        }

        StatementStrategy st = new AddStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public void addV3(final User user) throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(conn -> {
            PreparedStatement ps = conn.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            return ps;
        });
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectionMaker.makeConnection();

            ps = conn.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (rs != null) { try { rs.close(); } catch (SQLException e) {} }
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (conn != null) { try { conn.close(); } catch (SQLException e) {} }
        }
    }
}
