package me.jsj.domain.user.dao.chapter1.v5;

public class UserDaoCh1V5 {

    private static UserDaoCh1V5 INSTANCE;

    private UserDaoCh1V5() {
    }

    public static synchronized UserDaoCh1V5 getInstance() {
        if (INSTANCE == null) INSTANCE = new UserDaoCh1V5();
        return INSTANCE;
    }
}
