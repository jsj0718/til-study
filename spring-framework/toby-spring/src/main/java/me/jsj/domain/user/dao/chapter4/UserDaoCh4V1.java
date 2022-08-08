package me.jsj.domain.user.dao.chapter4;

import me.jsj.domain.user.UserV1;

import java.util.List;

public interface UserDaoCh4V1 {
    void add(UserV1 user);
    UserV1 get(String id);
    List<UserV1> getAll();
    void deleteAll();
    int getCount();
}
