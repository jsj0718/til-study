package me.jsj.chapter1.user.dao.v4;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;

public class AccountDao {
    private ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

}
