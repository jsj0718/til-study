package me.jsj.chapter3.user.dao.v3.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
