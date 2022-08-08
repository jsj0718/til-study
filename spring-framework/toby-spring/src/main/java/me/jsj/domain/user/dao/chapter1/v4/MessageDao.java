package me.jsj.domain.user.dao.chapter1.v4;

import me.jsj.domain.user.dao.chapter1.v3.ConnectionMaker;

public class MessageDao {
    private ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

}
