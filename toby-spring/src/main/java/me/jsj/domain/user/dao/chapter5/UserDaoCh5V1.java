package me.jsj.domain.user.dao.chapter5;

import me.jsj.domain.user.UserV2;

import java.util.List;

public interface UserDaoCh5V1 {
    void add(UserV2 user);
    UserV2 get(String id);
    List<UserV2> getAll();
    void deleteAll();
    int getCount();
    void update(UserV2 user);
}
