package me.jsj.chapter5.user.service;

import me.jsj.chapter5.user.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
