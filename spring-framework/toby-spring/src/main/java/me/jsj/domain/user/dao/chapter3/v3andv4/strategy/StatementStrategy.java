package me.jsj.domain.user.dao.chapter3.v3andv4.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makePreparedStatement(Connection conn) throws SQLException;
}
