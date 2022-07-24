package me.jsj.domain.user.dao.chapter3.v3andv4.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
