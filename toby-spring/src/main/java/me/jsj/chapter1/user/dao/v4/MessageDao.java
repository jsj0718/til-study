package me.jsj.chapter1.user.dao.v4;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;

public class MessageDao {
    private ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

}
