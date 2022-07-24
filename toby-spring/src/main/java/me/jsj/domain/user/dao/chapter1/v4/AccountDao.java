package me.jsj.domain.user.dao.chapter1.v4;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;

public class AccountDao {
    private ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

}
