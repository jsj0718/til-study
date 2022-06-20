package me.jsj.chapter3.user.dao.v3.templatemethod;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDaoV3 {

    public UserDaoDeleteAll(ConnectionMaker connectionMaker) {
        super(connectionMaker);
    }

    @Override
    protected PreparedStatement makeStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
