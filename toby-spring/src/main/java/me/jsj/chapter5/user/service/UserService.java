package me.jsj.chapter5.user.service;

import lombok.RequiredArgsConstructor;
import me.jsj.chapter5.user.dao.UserDao;
import me.jsj.chapter5.user.domain.Level;
import me.jsj.chapter5.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserLevelUpgradePolicy {

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    private final UserDao userDao;

/*
    //V1
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        users.stream().forEach(user -> {
            Boolean changed = null;
            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            } else if (user.getLevel() == Level.GOLD) {
                changed = false;
            } else {
                changed = false;
            }

            if (changed) {
                userDao.update(user);
            }
        });
    }
*/
    //V2
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        users.stream().forEach(user -> {
            if (canUpgradeLevel(user)) upgradeLevel(user);
        });
    }

    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
}
