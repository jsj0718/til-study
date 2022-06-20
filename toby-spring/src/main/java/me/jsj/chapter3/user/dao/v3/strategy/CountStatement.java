package me.jsj.chapter3.user.dao.v3.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CountStatement implements StatementStrategy {

    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("select count(*) from users");
    }
}
