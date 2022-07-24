package me.jsj.domain.user.service.chapter5;

import me.jsj.domain.user.UserV2;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(UserV2 user);
    void upgradeLevel(UserV2 user);
}
