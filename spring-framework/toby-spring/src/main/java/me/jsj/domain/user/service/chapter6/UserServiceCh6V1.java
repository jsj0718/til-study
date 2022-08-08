package me.jsj.domain.user.service.chapter6;

import me.jsj.domain.user.UserV2;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserServiceCh6V1 {
    void add(UserV2 user);
    void deleteAll();
    void update(UserV2 user);
    void upgradeLevels();

    @Transactional(readOnly = true)
    UserV2 get(String id);
    @Transactional(readOnly = true)
    List<UserV2> getAll();
}
