package me.jsj.chapter1.user.dao.v5;

import me.jsj.chapter1.user.dao.v3.ConnectionMaker;

import java.net.ConnectException;

public class UserDaoV5 {

    private static UserDaoV5 INSTANCE;

    private UserDaoV5() {
    }

    public static synchronized UserDaoV5 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDaoV5();
        return INSTANCE;
    }
}
