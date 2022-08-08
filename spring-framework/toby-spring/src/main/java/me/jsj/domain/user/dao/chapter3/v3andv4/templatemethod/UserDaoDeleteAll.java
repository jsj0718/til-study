package me.jsj.domain.user.dao.chapter3.v3andv4.templatemethod;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDaoCh3V4 {

    public UserDaoDeleteAll(ConnectionMaker connectionMaker) {
        super(connectionMaker);
    }

    @Override
    protected PreparedStatement makeStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
