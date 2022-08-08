package me.jsj.domain.user.dao.chapter6;

import lombok.Getter;
import me.jsj.domain.user.UserV2;
import me.jsj.domain.user.dao.chapter5.UserDaoCh5V1;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MockUserDao implements UserDaoCh5V1 {

    private List<UserV2> users;
    private List<UserV2> updated = new ArrayList<>();

    public MockUserDao(List<UserV2> users) {
        this.users = users;
    }

    @Override
    public List<UserV2> getAll() {
        return users;
    }

    @Override
    public void update(UserV2 user) {
        updated.add(user);
    }

    @Override
    public void add(UserV2 user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserV2 get(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException();
    }
}
